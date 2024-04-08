import java.text.SimpleDateFormat
import java.util.*

plugins {
    id("java")
    id("java-library")
    id("idea")

    alias(libs.plugins.forge.gradle)
    alias(libs.plugins.mixin.gradle)
}

val modId = Constants.Mod.id
val minecraftVersion: String = libs.versions.minecraft.get()

version = Constants.Mod.version
group = Constants.Mod.group

base {
    archivesName = "${Constants.Mod.name}-$minecraftVersion"
}

mixin {
    add(sourceSets["main"], "${modId}.refmap.json")

    config("${modId}.mixins.json")
}

minecraft {
    mappings("official", minecraftVersion)

    copyIdeResources = true

    file("src/main/resources/META-INF/accesstransformer.cfg").takeIf(File::exists)?.let(::accessTransformer)

    runs {
        create("client") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            taskName("Forge Client")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("server") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            taskName("Forge Server")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
        }

        create("data") {
            workingDirectory(project.file("run"))
            ideaModule("${rootProject.name}.${project.name}.main")
            args(
                "--mod",
                modId,
                "--all",
                "--output",
                file("src/generated/resources/"),
                "--existing",
                file("src/main/resources/")
            )
            taskName("Data")
            mods {
                create(modId) {
                    source(sourceSets.main.get())
                }
            }
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
        name = "Mekanism"
        url = uri("https://modmaven.dev/")
    }
}

dependencies {
    minecraft(libs.minecraftforge)

    libs.mekanism.get().run {
        compileOnly(fg.deobf("$module:$version:api"))
        compileOnly(fg.deobf("$module:$version:generators"))

        runtimeOnly(fg.deobf("$module:$version:all"))
    }

    annotationProcessor(variantOf(libs.mixin) {
        classifier("processor")
    })
}

tasks {
    withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 17
    }

    java {
        withSourcesJar()
        toolchain.languageVersion = JavaLanguageVersion.of(17)
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    processResources {
        val prop = mapOf(
            "version" to version,
            "group" to project.group,
            "minecraft_version" to minecraftVersion,
            "forge_version" to libs.versions.forge.get(),
            "forge_loader_version_range" to libs.versions.forgeRange.get(),
            "forge_version_range" to libs.versions.forgeRange.get(),
            "minecraft_version_range" to libs.versions.minecraftRange.get(),
            "mod_name" to Constants.Mod.name,
            "mod_author" to Constants.Mod.author,
            "mod_id" to Constants.Mod.id,
            "license" to Constants.Mod.license,
            "description" to Constants.Mod.description,
            "display_url" to Constants.Mod.repositoryUrl,
            "issue_tracker_url" to Constants.Mod.issueTrackerUrl
        )

        filesMatching(listOf("pack.mcmeta", "META-INF/mods.toml", "*.mixins.json")) {
            expand(prop)
        }
        inputs.properties(prop)
    }

    jar {
        from(rootProject.file("LICENSE")) {
            rename { "LICENSE_${Constants.Mod.id}" }
        }

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

    named<Jar>("sourcesJar") {
        from(rootProject.file("LICENSE")) {
            rename { "LICENSE_${Constants.Mod.id}" }
        }
    }
}
