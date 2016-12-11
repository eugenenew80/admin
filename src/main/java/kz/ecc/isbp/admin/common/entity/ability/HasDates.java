package kz.ecc.isbp.admin.common.entity.ability;

import java.util.Date;

public interface HasDates  {
	Date getCreateDate();
	Date getUpdateDate();
	
	void setCreateDate(Date createDate);
	void setUpdateDate(Date updateDate) ;
}
