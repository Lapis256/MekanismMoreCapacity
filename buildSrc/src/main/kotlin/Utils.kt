import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.provider.Provider


enum class Order {
    NONE,
    BEFORE,
    AFTER;
}

enum class Side {
    CLIENT,
    SERVER,
    BOTH;
}

data class ModDep(
    val id: String,
    val version: String,
    val mandatory: Boolean = true,
    val ordering: Order = Order.NONE,
    val side: Side = Side.BOTH
)

fun buildDeps(
    vararg deps: ModDep
): String {
    return deps.joinToString(separator = "\n") { (id, version, mandatory, ordering, side) ->
        """
            [[dependencies.${Constants.Mod.id}]]
            modId = "$id"
            versionRange = "[$version,)"
            mandatory = $mandatory
            ordering = "$ordering"
            side = "$side"
        """.trimIndent()
    }
}

fun DependencyHandler.variantOf(dependency: Provider<MinimalExternalModuleDependency>, classifier: String) =
    variantOf(dependency) { classifier(classifier) }
