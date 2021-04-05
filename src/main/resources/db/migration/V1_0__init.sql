--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS users (
  user_id bigint(20) NOT NULL,
  title varchar(255) DEFAULT NULL,
  first_name varchar(255) DEFAULT NULL,
  last_name varchar(255) DEFAULT NULL,
  email varchar(255) DEFAULT NULL,
  mobile varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  role varchar(255) DEFAULT NULL,
  token varchar(255) DEFAULT NULL,
  date_registered datetime NOT NULL,
  verified bit(1) DEFAULT NULL,
  date_verified datetime DEFAULT NULL,
  date_deactivated datetime DEFAULT NULL,
  status varchar(255) DEFAULT NULL
) ENGINE=MyISAM;

-- Indexes for table `users`
--
ALTER TABLE users
  ADD PRIMARY KEY (user_id);

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE users
  MODIFY user_id bigint(20) NOT NULL AUTO_INCREMENT;