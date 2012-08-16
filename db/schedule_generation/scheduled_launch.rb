class ScheduledLaunch < ActiveRecord::Base
  belongs_to :schedule
  
  validates :desired_gear, :numericality => {:only_integer => true, :greater_than => 0, :less_than_or_equal_to => 10}
end