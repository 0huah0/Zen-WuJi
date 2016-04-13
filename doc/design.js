TangMonk:｛
	categories:{
		discuss:[{
			input:{
				pattern:'今天星期*'，			//*号模糊匹配
				regular:'(\w)*星期(\w){0,1}'	//正则表达式
			},
			output:{
				default:'是美好的一天啊',		//如果random为空则返回这个
				random:['a','b','c'],	//从这些中随机返回一个值
				md:java.class			//machine_decision
			}
		},{
			input:{
				pattern:'*新闻*'，		//*号模糊匹配
				regular:'(\w)+新闻(\w)*'	//正则表达式
			},
			output:{
				default:'新华网是国内比较权威的新闻网站。',		//如果random为空则返回这个
				random:['a','b','c'],	//从这些中随机返回一个值
				md:java.class			//machine_decision
			}
		}],
		book:{},
		food:{},
		game:{},
		job:{},
		joke:{},
		life:{},
	},
	default_output:{
		default:'系统无法识别',				//如果random为空则返回这个
		random:['是什么意思？','抱歉，我回答不了。','不懂'],
		md:java.class			//machine_decision
	},
	user:{
		u_id:'session',	//用户表示父
		ext_attr:[{name:'jim'},{sex:'man'}],
		u_type:{		//用户画像
			style:[
				'幽默',
				'豪放',
				'强迫症',
				'随意',
				'思维跳跃',
				'嘴贱',
				'自卑',
				'自大'],	//由历史回话分类其聊天风格
			interest:[['85后',90%]], //tags的前5
			tags:[['85后',90%],['jay迷',0.7],['电影',36]]	//%\小数是概率，整数是出现次数
		},
		interaction:{
			resp_time_delay:{			//回复时间间隔随机范围
				default:[0.5,3],
				md:java.class			//machine_decision 用java类决策，考虑对话上下文
			},
			active:{		 //主动发言,0.问好；1.尝试激活话题，尝试次数try_times；2.尝试发起新话题，发一次。
				try_times:3, //尝试次数
				start_from_last_talk:10s,	//据上次谈话点多久启动主动发言
				random_topic:['你喜欢哪个歌星？','在干嘛？','hello','最近有什么电影值得推荐吗'],	
				topic_choice_md:java.class	 	//用java类选择话题
			},
			last_io_point:20151015105215,	//上次互动时间点
			last_io_point_expire:5s,		//上次互动点过期时间
			history:{ 						//用YYYYMMDDHHmmss时间戳做一次发言的
				u:[
					[20151015105211,VOICE,'你好'],//[互动时间点,交互类型,内容]
					[20151015105212,TEXT,'真不会撒谎'],
					[20151015105213,TEXT,'算了就算了'],
					[20151015105213,ACTION,'[表情(无聊);动作(无奈);离开(速度0.6)]']
				],
				m:[
					[20151015105211,TEXT,'你好，我不是机器人。:—）'],
					[20151015105212,VOICE,'不信算了'],
					[20151015105215,VOICE,'别走啊','算了，不理我了吗？']
				]
			}
		}
	}

｝