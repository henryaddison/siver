#!/usr/bin/env ruby
require "rubygems"
require "bundler/setup"

# require your gems as usual
require 'active_record'
require 'optparse'

random_seed = rand(2**31)
control_policies = []
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
  
  opts.on('-c', '--control-policies CONTROL_POLICY_TYPES', Array, "List of control policy class names") do |cps|
    control_policies = cps
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
  control_policies.each do |cp|
    schedules.each do |sch|
      experiment = Experiment.create!(
        :schedule => sch, 
        :random_seed => random_seed, 
        :control_policy => cp)
      end
  end
end