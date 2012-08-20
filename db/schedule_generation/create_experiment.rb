#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"

# require your gems as usual
require 'active_record'
require 'optparse'

random_seed = rand(2**31)
brain_types = ["BasicBrain"]
schedule_ids = []

optparse = OptionParser.new do|opts|
  opts.on( '-s', Array, "Schedule IDs" ) do |sids|
    schedule_ids = sid
  end
  
  opts.on('--all-schedules') do 
    schedule_ids = :all
  end
  
  opts.on('-r', '--random-seed RSEED', "Random seed") do |rs|
    random_seed = rs
  end
  
  opts.on('-b', '--brain-types BRAIN_TYPES', Array, "List of brain class names") do |bts|
    brain_types = bts
  end
  
  opts.on("-h", "--help", "Show this message") do
     puts opts
     exit
   end
end

optparse.parse!

require './schedule'
require './experiment'
require './scheduled_launch'
require './db_connect'

schedules = Schedule.find(schedule_ids)

Experiment.transaction do
  brain_types.each do |brain|
    schedules.each do |sch|
      experiment = Experiment.create!(
        :schedule => sch, 
        :random_seed => random_seed, 
        :brain_type => brain)
      end
  end
end