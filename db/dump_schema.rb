#!/usr/bin/env ruby
db_env = ENV['DB_ENV'] || "development"
`mysqldump -u siver siver_#{db_env} --no-data --ignore-table=siver_#{db_env}.migrations > dumps/#{db_env}_schema.sql`
`mysqldump -u siver siver_#{db_env} migrations >> dumps/#{db_env}_schema.sql`