#!/usr/bin/env ruby

if ARGV[0] == 'make'
  puts
  puts 'Building using maven...'
  if system 'mvn package'
    exit 0
  else
    exit 1
  end
elsif ARGV[0] == 'install'
  puts
  puts 'Installing gem dependencies...'
  if system 'bundle install'
    puts '  installed gem dependencies'
  else
    abort 'gem dependencies installation failed'
  end

  puts
  puts 'Installing artifact dependencies...'
  if system 'mvn install:install-file -DgroupId=com.sk89q -DartifactId=worldedit -Dversion=5.4.2 -Dpackaging=jar -Dfile=lib/WorldEdit.jar'
    puts '  installed worldedit'
  else
    abort 'worldedit artifact installation failed'
  end

  exit 0
end