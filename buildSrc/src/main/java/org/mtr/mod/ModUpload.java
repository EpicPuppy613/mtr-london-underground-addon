package org.mtr.mod;

import com.jonafanho.apitools.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ModUpload {

	private static final String[] MINECRAFT_VERSIONS = {"1.16.5", "1.17.1", "1.18.2", "1.19.2", "1.19.4", "1.20.1", "1.20.4"};

	public static void main(String[] args) throws IOException {
		if (args.length == 3) {
			for (final String minecraftVersion : MINECRAFT_VERSIONS) {
				for (final ModLoader modLoader : ModLoader.values()) {
					final String modVersion = String.format("%s-%s+%s", modLoader.name, args[0], minecraftVersion);
					final String modVersionUpperCase = String.format("%s-%s+%s", modLoader.name.toUpperCase(Locale.ENGLISH), args[0], minecraftVersion);
					final String fileName = String.format("MTR-London-Underground-Addon-%s.jar", modVersion);
					final Path filePath = Paths.get("build/release").resolve(fileName);

					final Map<String, DependencyType> dependenciesCurseForge = new HashMap<>();
					if (modLoader == ModLoader.FABRIC) {
						dependenciesCurseForge.put("fabric-api", DependencyType.REQUIRED);
					}
					dependenciesCurseForge.put("minecraft-transit-railway", DependencyType.REQUIRED);

					do {
					} while (!new ModId("545686", ModProvider.CURSE_FORGE).uploadFile(
							"",
							modVersionUpperCase,
							"See Discord",
							dependenciesCurseForge,
							ReleaseStatus.BETA,
							Collections.singleton(minecraftVersion),
							Collections.singleton(modLoader),
							false,
							Files.newInputStream(filePath),
							fileName,
							args[1]
					));

					final Map<String, DependencyType> dependenciesModrinth = new HashMap<>();
					if (modLoader == ModLoader.FABRIC) {
						dependenciesModrinth.put("P7dR8mSH", DependencyType.REQUIRED);
					}
					dependenciesModrinth.put("XKPAmI6u", DependencyType.REQUIRED);
					dependenciesModrinth.put("8pXSjgW8", DependencyType.INCOMPATIBLE);

					do {
					} while (!new ModId("jrAXj11W", ModProvider.MODRINTH).uploadFile(
							modVersionUpperCase,
							modVersionUpperCase,
							"See Discord",
							dependenciesModrinth,
							ReleaseStatus.BETA,
							Collections.singleton(minecraftVersion),
							Collections.singleton(modLoader),
							false,
							Files.newInputStream(filePath),
							fileName,
							args[2]
					));
				}
			}
		}
	}
}
