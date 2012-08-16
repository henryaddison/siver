db_env = ENV["DB_ENV"] || "development"

ActiveRecord::Base.establish_connection(
   :adapter  => "mysql2",
   :username => "siver",
   :database => "siver_#{db_env}"
)