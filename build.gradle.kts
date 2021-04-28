plugins {
    application
    kotlin("jvm") version "1.5.0-RC"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform(kotlin("bom")))
    implementation(kotlin("stdlib-jdk8"))
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("lymiah.ioof.datediff.MainKt")
}

tasks {
    test {
        useJUnitPlatform()
    }
    jar {
        manifest {
            attributes(
                mapOf(
                    "Main-Class" to application.mainClass
                )
            )
        }
        from({
            configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
        }) {
            exclude("META-INF", "META-INF/**")
        }
    }
}
