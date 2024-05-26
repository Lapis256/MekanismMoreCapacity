import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("java")
    id("java-library")
    id("idea")

    alias(libs.plugins.forge)
    alias(libs.plugins.mixin)
    alias(libs.plugins.parchmentmc)
}

val modId = Constants.Mod.id
val minecraftVersion: String = libs.versions.minecraft.get()
val forgeMajorVersion: String = libs.versions.forge.get().split(".").first()
val jdkVersion = 17


base {
    archivesName = "${project.name}-$minecraftVersion"
    version = Constants.Mod.version
    group = Constants.Mod.group
}

mixin {
    add(sourceSets["main"], "${modId}.refmap.json")

    config("${modId}.mixins.json")
}

minecraft {
    mappings("parchment", "${libs.versions.parchmentmc.get()}-$minecraftVersion")

    copyIdeResources = true

    file("src/main/resources/META-INF/accesstransformer.cfg").takeIf(File::exists)?.let(::accessTransformer)

    runs {
        configureEach {
            ideaModule("${rootProject.name}.${project.name}.main")

            properties(
                mapOf(
                    "forge.logging.markers" to "REGISTRIES", "forge.logging.console.level" to "debug"
                )
            )

            jvmArgs(
                "-XX:+AllowEnhancedClassRedefinition"
            )

            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("client") {
            taskName("Forge Client")

            workingDirectory(project.file("run"))

            property("forge.enabledGameTestNamespaces", modId)
        }

        create("server") {
            taskName("Forge Server")

            workingDirectory(project.file("run-server"))

            property("forge.enabledGameTestNamespaces", modId)
        }

        create("data") {
            taskName("Generate Data")

            workingDirectory(project.file("run-data"))

            args(
                "--mod",
                modId,
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources/")
            )
        }
    }
}

sourceSets["main"].resources.srcDir("src/generated/resources")

repositories {
    mavenCentral()
    maven {
        name = "Sponge / Mixin"
        url = uri("https://repo.spongepowered.org/repository/maven-public/")
    }
    maven {
        name = "Mekanism / JEI"
        url = uri("https://modmaven.dev/")
    }
}

dependencies {
    minecraft(libs.minecraftForge)

    compileOnly(deobf(libs.mekanism))
    compileOnly(deobf(variantOf(libs.mekanism, "api")))
    compileOnly(deobf(variantOf(libs.mekanism, "generators")))

    runtimeOnly(deobf(variantOf(libs.mekanism, "all")))

    runtimeOnly(deobf(libs.jei))

    annotationProcessor(variantOf(libs.mixin, "processor"))
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 17
    }

    java {
        withSourcesJar()
        toolchain {
            languageVersion = JavaLanguageVersion.of(jdkVersion)
            vendor = JvmVendorSpec.JETBRAINS
        }
        JavaVersion.toVersion(jdkVersion).let {
            sourceCompatibility = it
            targetCompatibility = it
        }
    }

    processResources {
        val prop = mapOf(
            "version" to version,
            "group" to project.group,
            "minecraft_version" to minecraftVersion,
            "mod_loader" to "javafml",
            "mod_loader_version_range" to "[$forgeMajorVersion,)",
            "mod_name" to Constants.Mod.name,
            "mod_author" to Constants.Mod.author,
            "mod_id" to Constants.Mod.id,
            "license" to Constants.Mod.license,
            "description" to Constants.Mod.description,
            "display_url" to Constants.Mod.repositoryUrl,
            "issue_tracker_url" to Constants.Mod.issueTrackerUrl,

            "dependencies" to buildDeps(
                ModDep("forge", forgeMajorVersion),
                ModDep("minecraft", minecraftVersion),
                ModDep("mekanismgenerators", "10.2", ordering = Order.AFTER),
            ),
        )

        filesMatching(listOf("pack.mcmeta", "META-INF/mods.toml", "*.mixins.json")) {
            expand(prop)
        }
        inputs.properties(prop)
    }

    jar {
        manifest {
            attributes(
                "Specification-Title" to Constants.Mod.name,
                "Specification-Vendor" to Constants.Mod.author,
                "Specification-Version" to version,
                "Implementation-Title" to project.name,
                "Implementation-Version" to version,
                "Implementation-Vendor" to Constants.Mod.author,
                "Implementation-Timestamp" to SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(Date()),
                "Timestamp" to System.currentTimeMillis(),
                "Built-On-Java" to "${System.getProperty("java.vm.version")} (${System.getProperty("java.vm.vendor")})",
                "Built-On-Minecraft" to minecraftVersion,
            )
        }

        finalizedBy("reobfJar")
    }

    listOf("jar", "sourcesJar", "jarJar").forEach {
        named<Jar>(it) {
            from(rootProject.file("LICENSE")) {
                rename { "LICENSE_${Constants.Mod.id}" }
            }
        }
    }
}

fun deobf(dependency: Provider<MinimalExternalModuleDependency>) = fg.deobf(dependency.get())
