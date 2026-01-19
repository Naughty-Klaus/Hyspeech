#!/bin/bash
set -e

SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
BIN_DIR="$SCRIPT_DIR/.bin"
SERVER_DIR="$SCRIPT_DIR/server"
SRC_REF_DIR="$SCRIPT_DIR/src-ref"


# Détection de l'OS
detect_os() {
    case "$(uname -s)" in
        Linux*)   echo "linux-amd64" ;;
        Darwin*)  echo "darwin-amd64" ;;
        CYGWIN*|MINGW*|MSYS*) echo "windows-amd64.exe" ;;
        *)        echo "unknown" ;;
    esac
}

OS_SUFFIX=$(detect_os)
if [ "$OS_SUFFIX" = "unknown" ]; then
    echo "Erreur: OS non supporté"
    exit 1
fi

download() {
    mkdir -p "$BIN_DIR"

    echo "Téléchargement de hytale-downloader..."
    curl -L -o "$BIN_DIR/hytale-downloader.zip" "https://downloader.hytale.com/hytale-downloader.zip"
    unzip -o "$BIN_DIR/hytale-downloader.zip" -d "$BIN_DIR"
    rm "$BIN_DIR/hytale-downloader.zip"

    echo "Téléchargement de CFR..."
    curl -L -o "$BIN_DIR/cfr-0.152.jar" "https://www.benf.org/other/cfr/cfr-0.152.jar"

    # Rendre les binaires exécutables
    chmod +x "$BIN_DIR"/hytale-downloader-* 2>/dev/null || true

    echo "Téléchargement terminé."
}

setup() {

    DOWNLOADER="$BIN_DIR/hytale-downloader-$OS_SUFFIX"
    if [ ! -f "$DOWNLOADER" ]; then
        echo "Erreur: $DOWNLOADER non trouvé."
        echo "Exécutez d'abord: ./setup.sh --download"
        exit 1
    fi

    echo "Téléchargement du serveur Hytale..."
    if [ ! -d "$SERVER_DIR" ]; then
        mkdir $SERVER_DIR
    fi
    if [ ! -f "$SERVER_DIR/server.zip" ]; then
        "$DOWNLOADER" --download-path "$SERVER_DIR/server.zip"
    fi
    echo "Décompression de l'archive du serveur Hytale"
    unzip -o "$SERVER_DIR/server.zip" -d "$SERVER_DIR"
    echo "Téléchargement terminée."
}

update_safe() {
    echo "Are you sure ?"
    echo "It will delete the old server/Assets.zip server/server.zip server/Server/* "
    echo "Use ./setup.sh --update-sure"
}

update() {
    DOWNLOADER="$BIN_DIR/hytale-downloader-$OS_SUFFIX"
    echo "Téléchargement du serveur Hytale..."
    if [ ! -d "$SERVER_DIR" ]; then
        mkdir $SERVER_DIR
    fi
    if [ -f "$SERVER_DIR/server.zip" ]; then
        rm $SERVER_DIR/server.zip
    fi
    if [ -f "$SERVER_DIR/Assets.zip" ]; then
        rm $SERVER_DIR/Assets.zip
    fi
    if [ -d "$SERVER_DIR/Server.zip" ]; then
        rm -rf $SERVER_DIR/Server
    fi
    if [ ! -f "$SERVER_DIR/server.zip" ]; then
        "$DOWNLOADER" --download-path "$SERVER_DIR/server.zip"
    fi
    echo "Décompression de l'archive du serveur Hytale"
    unzip -o "$SERVER_DIR/server.zip" -d "$SERVER_DIR"
    echo "Téléchargement terminée."
}

decompile() {
    echo "Décompilation avec CFR..."
    java -jar "$BIN_DIR/cfr-0.152.jar" "$SERVER_DIR/Server/HytaleServer.jar" --outputdir "$SRC_REF_DIR"
    echo "Décompilation terminée."
}

case "$1" in
    --download) download ;;
    --setup)    setup ;;
    --decompile)decompile ;;
    --update)   update ;;
    *)
        echo "Usage: ./setup.sh [--download|--setup]"
        echo ""
        echo "Options:"
        echo "  --download  Télécharge les outils (hytale-downloader, CFR)"
        echo "  --setup     Configure l'environnement (télécharge le serveur)"
        echo "  --decompile Décompile le serveur"
        exit 1
        ;;
esac
