#!/usr/bin/env ruby
require 'sys/uname'
include Sys

def make
  puts 'Building using maven...'
  if system 'mvn package'
    exit 0
  else
    exit 1
  end
end

def install
  windows = Uname.sysname[0,9].downcase == 'microsoft'

  puts 'Installing gem dependencies...'
  unless system 'bundle install'
    abort 'gem dependencies installation failed'
  end

  puts
  puts 'Installing artifact dependencies...'
  if windows
    unless system 'mvn install:install-file -DgroupId=com.sk89q -DartifactId=worldedit -Dversion=5.4.2 -Dpackaging=jar -Dfile=lib/WorldEdit.jar >NUL'
      abort 'build: artifact installation failed'
    end
  else
    unless system 'mvn install:install-file -DgroupId=com.sk89q -DartifactId=worldedit -Dversion=5.4.2 -Dpackaging=jar -Dfile=lib/WorldEdit.jar >/dev/null'
      abort 'build: artifact installation failed'
    end
  end

  if windows
    if system 'mvn install -DskipTests=true >NUL'
      puts 'Installed dependencies.'
    else
      abort 'build: artifact installation failed'
    end
  else
    if system 'mvn install -DskipTests=true >/dev/null'
      puts 'Installed dependencies.'
    else
      abort 'build: artifact installation failed'
    end
  end
end

if ARGV[0] == 'make'
  make
elsif ARGV[0] == 'install'
  install
  exit 0
elsif ARGV[0] == 'all'
  install
  make
else
  puts 'build: Please choose a valid task (make, install, all)'
end