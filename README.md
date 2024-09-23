# Mango Combat
Mango combat is a combat implementation for Minestom.
The combat style is based around 1.8 but with noticeable differences
that make it unique.

It is important to note that this IS NOT designed to be exactly like 1.8,
and it IS NOT designed to be like latest.

## Development
This project is still very much in development and while it is playable
it is lacking a few features that we intend to add in the future.

Feel free to open an issue or pull request to contribute!

## Usage
You can add mango-combat to any Minestom project by adding the following
to your build:

<details>
<summary>
build.gradle.kts
</summary>

```kotlin
repositories {
    mavenCentral()
    maven("https://maven.serble.net/snapshots/")
}

dependencies {
implementation("net.mangolise:mango-combat:latest")
}
```
</details>

<details>
<summary>
build.gradle
</summary>

```groovy
repositories {
    maven { url 'https://maven.serble.net/snapshots/' }
}

dependencies {
    implementation 'net.mangolise:mango-combat:latest'
}
```
</details>

<details>
<summary>
pom.xml
</summary>

pom.xml
```xml
<repositories>
    <repository>
        <id>Serble</id>
        <url>https://maven.serble.net/snapshots/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>net.mangolise</groupId>
        <artifactId>mango-combat</artifactId>
        <version>latest</version>
    </dependency>
</dependencies>
```
</details>

Once the dependency is there you can create a config object to specify
how the combat system should operate.

```java
// Create the config object
CombatConfig config = CombatConfig.create()  // Create with defaults
        .withFakeDeath(true)  // Specify different values for options
        .withDisableBuiltinDeathMsgs(true)
        .withIframes(500)
        .withVoidDeath(true)
        .withAutomaticRespawn(true);
```

This is not necessarily a complete list of config values, see the
javadocs on each method for more information, or in this repository
see `src/main/java/net/mangolise/combat/CombatConfig.java`.

Once you can a config you can enable the custom combat with the following:  
```java
MangoCombat.enableGlobal(config);
```

This will register combat mechanic for the entire server.

## Events
Currently, you can listen to two events:  

<details>
<summary>
PlayerAttackEvent
</summary>
This is called when a player attacks another player using the combat
system, you can modify the damage amount or cancel the event.
Use `event.makeNonLethal()` to make sure that the hit doesn't kill
its victim.
</details>

<details>
<summary>
PlayerKilledEvent
</summary>
This is called when a player dies. You should use this instead
of the PlayerDeathEvent because it allows features like fake death
to work properly. If fake death is enabled then the Minestom
PlayerDeathEvent WILL NOT be called when a player is killed.

The event allows you to cancel the death, set a death message,
set the death text, get the player's killer and get the last
damage cause.
</details>