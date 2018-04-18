CREATE TABLE `financial_stock_base_info` (
  `id` varchar(36) NOT NULL,
  `stock_name` varchar(45) DEFAULT NULL,
  `stock_code` varchar(15) DEFAULT NULL,
  `stock_type` int(11) DEFAULT NULL COMMENT '1,上证；2，深证',
  `create_time` datetime DEFAULT NULL,
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

CREATE TABLE `financial`.`financial_stock_daily_mental_info` (
  `id` VARCHAR(36) NOT NULL,
  `stock_code` VARCHAR(15) NULL COMMENT '股票代码',
  `create_time` DATETIME NULL COMMENT '创建日期',
  `date` DATETIME NULL COMMENT '数据产生的时间',
  `pe_ttm` DECIMAL(18,4) NULL COMMENT '动态市盈率',
  `d_pe_ttm` DECIMAL(18,4) NULL COMMENT '扣非动态市盈率',
  `pb` DECIMAL(18,4) NULL COMMENT '市净率',
  `pb_wo_gw` DECIMAL(18,4) NULL COMMENT '不含商誉的市净率',
  `ps_ttm` DECIMAL(18,4) NULL COMMENT '滚动市销率',
  `dividend_r` DECIMAL(18,4) NULL COMMENT '股息率',
  `market_value` DECIMAL(18,4) NULL COMMENT '市值',
  PRIMARY KEY (`id`));

