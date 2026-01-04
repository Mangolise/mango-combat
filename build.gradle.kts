plugins {
    id("java")
    id("java-library")
    id("maven-publish")
}

var versionStr = System.getenv("GIT_COMMIT") ?: "dev"

group = "net.mangolise"
version = versionStr

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.minestom:minestom:2026.01.01-1.21.11")

    testImplementation("net.minestom:minestom:2026.01.01-1.21.11")
    testImplementation(platform("org.junit:junit-bom:6.1.0-M1"))
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform {
        failOnNoDiscoveredTests = false
    }
}

java {
    withSourcesJar()
}

publishing {
    repositories {
        maven {
            name = "serbleMaven"
            url = uri("https://maven.serble.net/snapshots/")
            credentials {
                username = System.getenv("SERBLE_REPO_USERNAME")?:""
                password = System.getenv("SERBLE_REPO_PASSWORD")?:""
            }
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }

    publications {
        create<MavenPublication>("mavenGitCommit") {
            groupId = "net.mangolise"
            artifactId = "mango-combat"
            version = versionStr
            from(components["java"])
        }

        create<MavenPublication>("mavenLatest") {
            groupId = "net.mangolise"
            artifactId = "mango-combat"
            version = "latest"
            from(components["java"])
        }
    }
}
