#!/usr/bin/env ruby
require 'sys/uname'
include Sys

# options
travis = false
verbose = false

def make
  puts 'Building using maven...'
  if system 'mvn package -source 5'
    exit 0
  else
    exit 1
  end
end

def install(travis, verbose)
  windows = Uname.sysname[0,9].downcase == 'microsoft'

  unless travis
    puts 'Installing gem dependencies...'
    unless system 'bundle install'
      abort 'gem dependencies installation failed'
    end
  end

  puts
  puts 'Installing artifact dependencies...'

  if verbose
    unless system 'mvn install:install-file -DgroupId=com.sk89q -DartifactId=worldedit -Dversion=5.4.2 -Dpackaging=jar -Dfile=lib/WorldEdit.jar'
      abort 'build: artifact installation failed'
    end

    if system 'mvn install -DskipTests=true'
      puts 'Installed dependencies.'
    else
      abort 'build: artifact installation failed'
    end
  else
    if windows
      unless system 'mvn install:install-file -DgroupId=com.sk89q -DartifactId=worldedit -Dversion=5.4.2 -Dpackaging=jar -Dfile=lib/WorldEdit.jar >NUL'
        abort 'build: artifact installation failed'
      end

      if system 'mvn install -DskipTests=true >NUL'
        puts 'Installed dependencies.'
      else
        abort 'build: artifact installation failed'
      end
    else
      unless system 'mvn install:install-file -DgroupId=com.sk89q -DartifactId=worldedit -Dversion=5.4.2 -Dpackaging=jar -Dfile=lib/WorldEdit.jar >/dev/null'
        abort 'build: artifact installation failed'
      end

      if system 'mvn install -DskipTests=true >/dev/null'
        puts 'Installed dependencies.'
      else
        abort 'build: artifact installation failed'
      end
    end
  end


end

options = []
ARGV.each do |arg|
  if arg[0,1] == '-'
    options << arg
  end
end

options.each do |option|
  if option == '--travis'
    travis = true
  elsif option == '-v' || option == '--verbose'
    verbose = true
  end
end

if ARGV[0] == 'make'
  make
elsif ARGV[0] == 'install'
  install travis, verbose
  exit 0
elsif ARGV[0] == 'all'
  install travis, verbose
  make
else
  puts 'build: Please choose a valid task (make, install, all)'
end