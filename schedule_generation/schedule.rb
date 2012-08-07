class Schedule < ActiveRecord::Base
  has_many :experiments
  has_many :scheduled_launches
end