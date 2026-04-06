# PlayerKits2 🎒

[![Spigot](https://img.shields.io/badge/Spigot-1.8%20--%201.21-orange.svg?style=flat-square)](https://www.spigotmc.org/resources/playerkits-2-fully-configurable-kits-1-8-1-20.112616/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat-square)](LICENSE)

**PlayerKits2** is an advanced and fully configurable kits plugin for Minecraft servers, originally developed by **Ajneb97**. It supports a wide range of Minecraft versions (1.8 through 1.21) and offers a rich set of features to customize your server kits exactly how you want them, either entirely via in-game GUIs or config files.

## 🌟 Features

- **Fully Configurable**: Custom items, enchantments, lore, names, metadata, and NBT tags.
- **Interactive GUI**: Players can preview, claim, and interact with your kits seamlessly through intuitive built-in menus.
- **Robust Requirements & Cooldowns**: Set per-kit permissions, price constraints, one-time limits, or time-based cooldowns.
- **Smart Integrations**: 
  - **[PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)** integration for dynamic custom placeholders inside kit GUIs.
  - **[Vault](https://www.spigotmc.org/resources/vault.34315/)** integration for economy-based kit prices and requirements.
- **Database Support**: HikariCP integrated for safe and fast operations with SQLite/MySQL.
- **Extensive Version Support**: Works smoothly on Spigot and Paper forks across multiple versions.

## 📥 Installation

1. Download the latest `.jar` file from the [SpigotMC Official Page](https://www.spigotmc.org/resources/playerkits-2-fully-configurable-kits-1-8-1-20.112616/).
2. Upload the downloaded `.jar` into your server's `plugins` folder.
3. *(Optional)* Install **Vault** and/or **PlaceholderAPI** to enable additional economy/placeholder integration capabilities.
4. Restart your server.
5. Create and configure your first kits in-game using commands or via the YAML files in `plugins/PlayerKits2/`.

## 🎮 Commands & Permissions

The main command for the plugin is `/kit`.
Additional aliases: `/kits`, `/playerkits`

*(For detailed permissions and sub-commands, please refer to the plugin's main documentation on SpigotMC or in-game help menu).*

## 🛠️ Building from Source

To compile the plugin from source, you will need **Java 21** and **Maven** installed on your system.

```bash
git clone <your-repository-url>
cd PlayerKits2
mvn clean package
```
The compiled plugin `.jar` will be generated inside the `target/` directory.

## 📜 License

This project is licensed under the terms of the [MIT License](LICENSE).
