ALTER TABLE `characters`
ADD COLUMN `hptoheal`  int(11) NOT NULL DEFAULT 0 AFTER `gachexp`,
ADD COLUMN `hppotion`  int(11) NOT NULL AFTER `hptoheal`,
ADD COLUMN `mptoheal`  int(11) NOT NULL AFTER `hppotion`,
ADD COLUMN `mppotion`  int(11) NOT NULL AFTER `mptoheal`;