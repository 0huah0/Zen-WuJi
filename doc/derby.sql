/**
 * 其中
 * if(lang = en){
 * 	dict_simple=dict_result
 * 	dict_detail=dict_example
 * 	dict_pinyin=dict_similar
 * }
 */
create table word_dict(
	word varchar(100) primary key,
	lang varchar(20), 
	trans_result varchar(500),
	dict_simple varchar(500),
	dict_detail varchar(1000),
	dict_pinyin varchar(500),
	baike_link varchar(1000),
	baike_image varchar(1000),
	baike_content varchar(3000)
)

/**
create table word_dict(	word varchar(100) primary key,	lang varchar(20), 	trans_result varchar(500),	dict_simple varchar(500),	dict_detail varchar(1000),	dict_pinyin varchar(500),	baike_link varchar(1000),	baike_image varchar(1000),	baike_content varchar(3000))
alter table User alter column name set data type VARCHAR(50)
*/
