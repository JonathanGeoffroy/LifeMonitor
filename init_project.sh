#!/bin/bash
RED="31"
GREEN="32"
WOS=""

function makeItColorful {
    echo -e "\e[$2m$1\e[0m"
}

function is_working {
    if [ $? -eq 0 ];then
        makeItColorful "Réussite : $1" $GREEN
    else
        makeItColorful "Echec : $1" $RED
    fi
}

function detectOS {
    if [ -f /etc/lsb-release ]; then
        OS=$(cat /etc/lsb-release | grep DISTRIB_ID | sed 's/^.*=//')
        VERSION=$(cat /etc/lsb-release | grep DISTRIB_RELEASE | sed 's/^.*=//')
        if [ "$OS" = "Ubuntu" ] || [ "$OS" = "Debian" ] ;then
            WOS="$OS"
        fi
    elif [ -f /etc/redhat-release ]; then
        WOS="Fedora"
    elif [ -f /etc/centos-release ]; then
        WOS="CentOS"
    else
        WOS="WTF ?"
    fi
}

function home_ln {
    ln -sf `pwd`/$1 -t ~/ > /dev/null 2>&1
    is_working "Création de $1 sur ~"
}

function home_cp {
    unalias cp > /dev/null 2>&1
    cp -fr `pwd`/$1 ~/$1 > /dev/null 2>&1
    is_working "Copie de $1 sur ~"
    alias cp="cp -iv" > /dev/null 2>&1
}

function cp_to {
    unalias cp > /dev/null 2>&1
    cp -fr `pwd`/$1 $2 > /dev/null 2>&1
    is_working "Copie de $1 sur $2"
    alias cp="cp -iv" > /dev/null 2>&1    
}

function rm_to {

}


# Faire un detectOS avant
function ins {
    all="$@" # pour fonction is_working
    echo "Installation: $all ...."
    if [ "$WOS" = "Ubuntu" ] || [ "$WOS" = "Debian" ] ;then
        sudo aptitude update -y  > /dev/null 2>&1
        sudo aptitude install $@ -y > /dev/null 2>&1
        is_working "Installation de $all"
    elif [ "$WOS" = "Fedora" ] ;then
        sudo dnf install $@ -y > /dev/null 2>&1
        is_working "Installation de $all"
    else
        makeItColorful "OS Inconnu" $RED
    fi
}

function make {
    detectOS
    cp_to debug.keystore ~/.android/.
}
make $@
exit 0

# Local Variables:
# mode: Shell-script
# coding: mule-utf-8
# End:
