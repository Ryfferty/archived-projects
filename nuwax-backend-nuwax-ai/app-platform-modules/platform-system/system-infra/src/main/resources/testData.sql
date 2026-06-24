USE nga;

-- 插入测试数据到user表
INSERT INTO user (username, avatar, email,email_verify_code, wallet_address, register_date, last_login_time, account_status)
VALUES
    ('User1', 'http://example.com/avatar1.jpg', 'user1@example.com', '123456','0x123456789abcdef', '2024-01-01', '2024-07-23 10:00:00', 'Normal'),
    ('User2', 'http://example.com/avatar2.jpg', 'user2@example.com', '178945','0x234567890abcdef', '2024-01-02', '2024-07-23 11:00:00', 'Normal'),
    ('User3', 'http://example.com/avatar3.jpg', 'user3@example.com', '789456','0x345678901abcdef', '2024-01-03', '2024-07-23 12:00:00', 'Disabled'),
    ('User4', 'http://example.com/avatar4.jpg', 'user4@example.com','456123', '0x456789012abcdef', '2024-01-04', '2024-07-23 13:00:00', 'Normal'),
    ('User5', 'http://example.com/avatar5.jpg', 'user5@example.com', '852963','0x567890123abcdef', '2024-01-05', '2024-07-23 14:00:00', 'Normal');

# delete from `union`;
-- 插入测试数据到union表
INSERT INTO  `union` (union_name, union_description, union_tag, img_url, icon_url, user_count, user_base_count, social_media, union_status, union_admin)
VALUES
    ('Union1', 'Description for Union1', '["竞技","PVE"]', 'http://example.com/union1.jpg', 'http://example.com/icon1.jpg', 10, 5, '[ {"name": "X","icon": "x.png","url": "https://x.com"},{"name": "Telegram","icon": "telegram.png","url": "https://telegram.org"},{"name": "Medium","icon": "medium.png","url": "https://medium.com"}]', 'Normal', 'Admin1,Admin2'),
    ('Union2', 'Description for Union2', '["休闲","PVP"]', 'http://example.com/union2.jpg', 'http://example.com/icon2.jpg', 15, 8, '[ {"name": "X","icon": "x.png","url": "https://x.com"},{"name": "Telegram","icon": "telegram.png","url": "https://telegram.org"},{"name": "Medium","icon": "medium.png","url": "https://medium.com"}]', 'Pending', 'Admin3,Admin4'),
    ('Union3', 'Description for Union3', '["交易"]', 'http://example.com/union3.jpg', 'http://example.com/icon3.jpg', 20, 10, '[ {"name": "X","icon": "x.png","url": "https://x.com"},{"name": "Telegram","icon": "telegram.png","url": "https://telegram.org"},{"name": "Medium","icon": "medium.png","url": "https://medium.com"}]','Disabled', 'Admin5,Admin6'),
    ('Union4', 'Description for Union4', '["国际","PVE"]', 'http://example.com/union4.jpg', 'http://example.com/icon4.jpg', 25, 12, '[ {"name": "X","icon": "x.png","url": "https://x.com"},{"name": "Telegram","icon": "telegram.png","url": "https://telegram.org"},{"name": "Medium","icon": "medium.png","url": "https://medium.com"}]',  'Normal', 'Admin7,Admin8'),
    ('Union5', 'Description for Union5', '["教学","PVP"]', 'http://example.com/union5.jpg', 'http://example.com/icon5.jpg', 30, 15, '[ {"name": "X","icon": "x.png","url": "https://x.com"},{"name": "Telegram","icon": "telegram.png","url": "https://telegram.org"},{"name": "Medium","icon": "medium.png","url": "https://medium.com"}]',  'Pending', 'Admin9,Admin10');

-- 插入测试数据到super_node表
INSERT INTO super_node (union_id, node_name, node_ca, crowdfunding_start_date, crowdfunding_end_date, crowdfunding_hard_cap, profit_distribution_cycle, manage_fee_ratio, min_stake, total_staked, staking_start_time, staking_end_time, stake_duration, application_status, node_status, mark)
VALUES
    (1, 'Node1', '0xNodeCa1', '2024-07-01', '2024-07-15', 1000.00, 30, '0.01', 50.00, 0.0, '2024-07-16', '2024-08-15', 30, 'Approved', 'Applying', ''),
    (2, 'Node2', '0xNodeCa2', '2024-07-02', '2024-07-16', 2000.00, 60, '0.02', 100.00, 0.0, '2024-07-17', '2024-08-16', 30, 'Pending', 'Published', ''),
    (3, 'Node3', '0xNodeCa3', '2024-07-03', '2024-07-17', 3000.00, 90, '0.03', 150.00, 0.0, '2024-07-18', '2024-08-17', 30, 'Rejected', 'Crowdfunding', 'Rejected due to XYZ'),
    (4, 'Node4', '0xNodeCa4', '2024-07-04', '2024-07-18', 4000.00, 120, '0.04', 200.00, 0.0, '2024-07-19', '2024-08-18', 30, 'Approved', 'Staking', ''),
    (5, 'Node5', '0xNodeCa5', '2024-07-05', '2024-07-19', 5000.00, 150, '0.05', 250.00, 0.0, '2024-07-20', '2024-08-19', 30, 'Approved', 'Completed', '');

-- 插入测试数据到game_account表
INSERT INTO game_account (broker_id, union_id, game_name, account_detail, stake_amount, reward_ratio, rental_period, account_status, game_level, manage_fee_ratio, rent_user_id, rental_contact_info, rent_start_time,rent_end_time,platform_approval_time)
VALUES
    (1, 1, 'Game1', 'Details for Game1', 100.00, '50/50', 30, 'Available', 'Level1', '0.10', NULL, 'Contact1',NULL,NULL, NULL),
    (2, 2, 'Game2', 'Details for Game2', 200.00, '60/40', 60, 'Available', 'Level2', '0.15', NULL, 'Contact2',NULL,NULL, NULL),
    (3, 3, 'Game3', 'Details for Game3', 300.00, '70/30', 90, 'Rented', 'Level3', '0.20', 1, 'Contact3',NULL,NULL, '2024-07-22 09:00:00'),
    (4, 4, 'Game4', 'Details for Game4', 400.00, '80/20', 120, 'Expired', 'Level4', '0.25', NULL, 'Contact4',NULL,NULL, NULL),
    (5, 5, 'Game5', 'Details for Game5', 500.00, '90/10', 150, 'Applied', 'Level5', '0.30', NULL, 'Contact5',NULL,NULL, '2024-07-23 10:00:00');

-- 继续插入测试数据到union_admin表
INSERT INTO union_admin (union_id, name, status, role, password, wallet_address, email, last_login_time)
VALUES
    (1, 'AdminUnion1', 'Enabled', 'SuperAdmin', 'Pass1234', '0xWallet1', 'admin1@example.com', '2024-07-21 08:00:00'),
    (2, 'AdminUnion2', 'Disabled', 'Moderator', 'Pass5678', '0xWallet2', 'admin2@example.com', NULL),
    (3, 'AdminUnion3', 'Enabled', 'Member', 'Pass91011', '0xWallet3', 'admin3@example.com', '2024-07-22 08:00:00'),
    (4, 'AdminUnion4', 'Enabled', 'Moderator', 'Pass12345', '0xWallet4', 'admin4@example.com', '2024-07-23 08:00:00'),
    (5, 'AdminUnion5', 'Disabled', 'Member', 'Pass67890', '0xWallet5', 'admin5@example.com', '2024-07-24 08:00:00');

-- 插入测试数据到platform_admin表
INSERT INTO platform_admin (name, status, role, password, wallet_address, email, last_login_time)
VALUES
    ('PlatformAdmin1', 'Enabled', 'SuperAdmin', 'PassAdmin1', '0xPlatformWallet1', 'platformadmin1@example.com', '2024-07-21 09:00:00'),
    ('PlatformAdmin2', 'Disabled', 'Moderator', 'PassAdmin2', '0xPlatformWallet2', 'platformadmin2@example.com', NULL),
    ('PlatformAdmin3', 'Enabled', 'Member', 'PassAdmin3', '0xPlatformWallet3', 'platformadmin3@example.com', '2024-07-22 09:00:00'),
    ('PlatformAdmin4', 'Enabled', 'Moderator', 'PassAdmin4', '0xPlatformWallet4', 'platformadmin4@example.com', '2024-07-23 09:00:00'),
    ('PlatformAdmin5', 'Disabled', 'Member', 'PassAdmin5', '0xPlatformWallet5', 'platformadmin5@example.com', '2024-07-24 09:00:00');

COMMIT;