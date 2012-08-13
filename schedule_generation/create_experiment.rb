#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"

# require your gems as usual
require 'active_record'
require 'optparse'

require './schedule'
require './experiment'
require './scheduled_launch'
require './db_connect'

random_seed = rand(2**31)
brain_type = "BasicBrain"
schedule_id = nil

optparse = OptionParser.new do|opts|
  opts.on( '-s', "Schedule ID" ) do |sid|
    schedule_id = sid
  end
  
  opts.on('-r', '--random-seed RSEED', "Random seed") do |rs|
    random_seed = rs
  end
  
  opts.on('-b', '--brain-type BRAIN_TYPE', "Brain class name") do |bt|
    brain_type = bt
  end
end

optparse.parse!

schedule = Schedule.find(schedule_id)

Experiment.transaction do
  experiment = Experiment.create!(
    :schedule => schedule, 
    :random_seed => random_seed, 
    :brain_type => brain_type)
end