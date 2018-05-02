CREATE TABLE `financial_stock_base_info` (
  `id` varchar(36) NOT NULL,
  `stock_name` varchar(45) DEFAULT NULL,
  `stock_code` varchar(15) DEFAULT NULL,
  `stock_type` int(11) DEFAULT NULL COMMENT '1,上证；2，深证',
  `create_time` datetime DEFAULT NULL,
  `listed_time` datetime DEFAULT NULL COMMENT '上市时间',
  `delist` int(11) DEFAULT '0' COMMENT '是否退市：1，是；0，否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



CREATE TABLE `financial_stock_detail_day_record` (
  `id` varchar(36) NOT NULL,
  `jin_kai` decimal(18,2) DEFAULT NULL COMMENT '今日开盘价格，元',
  `zuo_shou` decimal(18,2) DEFAULT NULL COMMENT '昨日收盘价格，元',
  `cheng_jiao_liang` decimal(18,2) DEFAULT NULL COMMENT '成交量，万手',
  `huan_shou_lv` decimal(18,2) DEFAULT NULL COMMENT '换手率，%',
  `zui_gao` decimal(18,2) DEFAULT NULL COMMENT '最高股价',
  `zui_di` decimal(18,2) DEFAULT NULL COMMENT '最低股价，元',
  `zhang_ting` decimal(18,2) DEFAULT NULL COMMENT '涨停价格，元',
  `die_ting` decimal(18,2) DEFAULT NULL COMMENT '跌停股价，元',
  `nei_pan` decimal(18,2) DEFAULT NULL COMMENT '内盘成交量，万手',
  `wai_pan` decimal(18,2) DEFAULT NULL COMMENT '外盘成交量，万手',
  `cheng_jiao_e` decimal(18,2) DEFAULT NULL COMMENT '成交额，亿',
  `zhen_fu` decimal(18,2) DEFAULT NULL COMMENT '振幅，%',
  `wei_bi` decimal(18,2) DEFAULT NULL COMMENT '委比',
  `liang_bi` decimal(18,2) DEFAULT NULL COMMENT '量比',
  `liu_tong_shi_zhi` decimal(18,2) DEFAULT NULL COMMENT '流通市值，亿',
  `zong_shi_zhi` decimal(18,2) DEFAULT NULL COMMENT '总市值，亿',
  `shi_ying_lv` decimal(18,2) DEFAULT NULL COMMENT '市盈率',
  `shi_jing_lv` decimal(18,2) DEFAULT NULL COMMENT '市净率',
  `mei_gu_shou_yi` decimal(18,2) DEFAULT NULL COMMENT '每股收益，元',
  `mei_gu_jing_zi_chan` decimal(18,2) DEFAULT NULL COMMENT '每股净资产',
  `zong_gu_ben` decimal(18,2) DEFAULT NULL COMMENT '总股本，亿',
  `liu_tong_gu_ben` decimal(18,2) DEFAULT NULL COMMENT '流通股本，亿',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `stock_code` varchar(15) DEFAULT NULL COMMENT '股票编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `financial_stock_daily_mental_info` (
  `id` varchar(36) NOT NULL,
  `stock_code` varchar(15) DEFAULT NULL COMMENT '股票代码',
  `create_time` datetime DEFAULT NULL COMMENT '创建日期',
  `date` datetime DEFAULT NULL COMMENT '数据产生的时间',
  `pe_ttm` decimal(18,4) DEFAULT NULL COMMENT '动态市盈率',
  `d_pe_ttm` decimal(18,4) DEFAULT NULL COMMENT '扣非动态市盈率',
  `pb` decimal(18,4) DEFAULT NULL COMMENT '市净率',
  `pb_wo_gw` decimal(18,4) DEFAULT NULL COMMENT '不含商誉的市净率',
  `ps_ttm` decimal(18,4) DEFAULT NULL COMMENT '滚动市销率',
  `dividend_r` decimal(18,4) DEFAULT NULL COMMENT '股息率',
  `market_value` decimal(18,4) DEFAULT NULL COMMENT '市值',
  `crawled` int(2) DEFAULT '0' COMMENT '是否信息抓取过：0，否；1，是',
  `pb_degree` decimal(18,4) DEFAULT '0.0000' COMMENT '不含商誉的pb温度',
  `pe_ttm_degree` decimal(18,4) DEFAULT '0.0000' COMMENT '扣非动态市盈率温度',
  `stock_degree` decimal(18,4) DEFAULT '0.0000' COMMENT '股票温度',
  `degreed` int(2) DEFAULT '0' COMMENT '是否计算过股票温度：0，否；1，是',
  PRIMARY KEY (`id`),
  KEY `idx_financial_stock_daily_mental_info_stock_code` (`stock_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `financial`.`financial_stock_daily_crawl_task` (
  `id` VARCHAR(64) NOT NULL,
  `crawl_date` DATETIME NULL COMMENT '抓取时间',
  `task_type` INT NULL COMMENT '抓取类型：1，股票基本面信息',
  `task_status` INT NULL COMMENT '任务状态：0，未执行；1，执行成功；-1，执行失败',
  PRIMARY KEY (`id`));



