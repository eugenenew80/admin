package kz.ecc.isbp.admin.auth.dto;

import java.util.Set;

import kz.ecc.isbp.admin.fnd.dto.ModuleDto;

public class ListDataDto {
	public Set<Long> data;
	public Set<ModuleDto> modules;
	public Set<RoleModuleDto> roles;
	public Set<RoleModuleDictDto> dicts;
}
