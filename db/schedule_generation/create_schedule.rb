#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"

# require your gems as usual
require 'active_record'
require 'optparse'

schedule_name = nil
launch_delay = nil
boat_count = 100
version = 1

optparse = OptionParser.new do|opts|
  opts.on( '-s', '--schedule-name SNAME', "Schedule name" ) do |sn|
    schedule_name = sn
  end
  
  opts.on( '-v', '--schedule-version SCHEDULE_VERSION', Integer, "Schedule version") do |v|
    version = v
  end
  
  opts.on( '-b', '--boat-count BOAT_COUNT', Integer, "Boat count") do |bc|
    boat_count = bc
  end
  
  opts.on( '-d', '--launch-delay LAUNCH_DELAY', Integer, "Launch delay") do |ld|
    launch_delay = ld
  end
  
  opts.on_tail("-h", "--help", "Show this message") do
     puts opts
     exit
   end
end

optparse.parse!

require './schedule'
require './experiment'
require './scheduled_launch'
require './db_connect'

Schedule.transaction do
  schedule = Schedule.create!(:name => schedule_name, :version => v)
  boat_count.times do |i|
    ScheduledLaunch.create!(
      :schedule => schedule, 
      :desired_gear => rand(10)+1, 
      :launch_tick => i*launch_delay,
      # speed multiplier and distance to cover are not being varied in experiments currently
      :speed_multiplier => 0.5, 
      :distance_to_cover => 5000
      )
  end
end