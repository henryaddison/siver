#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"

# require your gems as usual
require 'active_record'
require 'optparse'

require './schedule'
require './experiment'
require './scheduled_launch'

schedule_name = nil
experiment_name = nil
random_seed = rand(2**31)
brain_type = "BasicBrain"

optparse = OptionParser.new do|opts|
  opts.on( '-s', '--schedule-name SNAME', "Schedule name" ) do |sn|
    schedule_name = sn
  end
  
  opts.on('-r', '--random-seed RSEED', "Random seed") do |rs|
    random_seed = rs
  end
  
  opts.on('-b', '--brain-type BRAIN_TYPE', "Brain class name") do |bt|
    brain_type = bt
  end
end

optparse.parse!

ActiveRecord::Base.establish_connection(
   :adapter  => "mysql2",
   :username => "siver",
   :database => "siver_development"
)

Schedule.transaction do

  schedule = Schedule.create!(:name => schedule_name)
  experiment = Experiment.create!(:schedule => schedule, :random_seed => random_seed)
  
  100.times do |i|
    ScheduledLaunch.create!(:schedule => schedule, :desired_gear => rand(10)+1, :speed_multiplier => 0.5, :distance_to_cover => 4000, :launch_tick => i*60, :brain_type => brain_type)
  end
end