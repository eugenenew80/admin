package kz.ecc.isbp.admin.common.entity.ability;

import kz.ecc.isbp.admin.auth.entity.User;

public interface HasAuthor {
	User getCreatedBy();
	void setCreatedBy(User user);
	User getUpdatedBy();
	void setUpdatedBy(User user);
}
