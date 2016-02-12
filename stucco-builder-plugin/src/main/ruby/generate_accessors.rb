#!/usr/bin/ruby

def capital(field)
  field.slice(0,1).capitalize + field.slice(1..-1)
end

ARGF.each do |line|
  if line =~ /private (DateValue|InexactValue) ([a-zA-Z]+);/
    puts "#{$1} get#{capital $2}();"
    puts "void set#{capital $2}(#{$1} #{$2});"
    puts ''
  elsif line =~ /private ([A-Z][a-z]+)Value ([a-zA-Z]+);/
    puts "#{$1}Value get#{capital $2}();"
    puts "#{$1} get#{capital $2}Value();"
    puts "void set#{capital $2}(#{$1}Value #{$2});"
    puts "void set#{capital $2}Value(#{$1} #{$2});"
    puts ''
  elsif line =~ /private ([a-zA-Z]+) ([a-zA-Z]+);/
    puts "#{$1} get#{capital $2}();"
    puts "void set#{capital $2}(#{$1} #{$2});"
    puts ''
  end
end