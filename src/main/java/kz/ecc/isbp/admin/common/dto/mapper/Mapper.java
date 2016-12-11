package kz.ecc.isbp.admin.common.dto.mapper;

import java.util.Arrays;
import java.util.function.Function;

import kz.ecc.isbp.admin.auth.dto.*;
import kz.ecc.isbp.admin.auth.entity.*;
import kz.ecc.isbp.admin.fnd.dto.DictDto;
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.nsi.dto.BudgetVerDto;
import kz.ecc.isbp.admin.nsi.dto.OrgStructDto;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import org.dozer.DozerBeanMapper;
import kz.ecc.isbp.admin.fnd.dto.ModuleDto;
import kz.ecc.isbp.admin.fnd.entity.Module;

public class Mapper {
	private final static DozerBeanMapper mapper;
	static {
		mapper = new DozerBeanMapper();
		mapper.setMappingFiles(Arrays.asList(
			"mapping/UserDtoMapping.xml",
			"mapping/ModuleDtoMapping.xml",
			"mapping/RoleModuleDtoMapping.xml",
			"mapping/RoleModuleDictDtoMapping.xml",
			"mapping/PermissionDtoMapping.xml",
			"mapping/RoleDtoMapping.xml",
			"mapping/DictDtoMapping.xml",
			"mapping/BudgetVerDtoMapping.xml",
			"mapping/OrgStructDtoMapping.xml"
		));	
	}
	
	public final static Function<User, UserDto> toUserDto = user -> mapper.map(user, UserDto.class);
	public final static Function<UserDto, User> fromUserDto = userDto -> mapper.map(userDto, User.class);
	
	public final static Function<Module, ModuleDto> toModuleDto = module -> mapper.map(module, ModuleDto.class);
	public final static Function<ModuleDto, Module> fromModuleDto = moduleDto -> mapper.map(moduleDto, Module.class);

    public final static Function<Dict, DictDto> toDictDto = dict -> mapper.map(dict, DictDto.class);
    public final static Function<DictDto, Dict> fromDictDto = dictDto -> mapper.map(dictDto, Dict.class);

	public final static Function<Role, RoleDto> toRoleDto = role -> mapper.map(role, RoleDto.class);
	public final static Function<RoleDto, Role> fromRoleDto = roleDto -> mapper.map(roleDto, Role.class);

	public final static Function<Permission, PermissionDto> toPermissionDto = permission -> mapper.map(permission, PermissionDto.class);
	public final static Function<PermissionDto, Permission> fromPermissionDto = permissionDto -> mapper.map(permissionDto, Permission.class);

    public final static Function<OrgStruct, OrgStructDto> toOrgStructDto = orgStruct -> mapper.map(orgStruct, OrgStructDto.class);
    public final static Function<OrgStructDto, OrgStruct> fromOrgStructDto = orgStructDto -> mapper.map(orgStructDto, OrgStruct.class);

	public final static Function<RoleModule, RoleModuleDto> toRoleModuleDto = roleModule -> mapper.map(roleModule, RoleModuleDto.class);
	public final static Function<RoleModuleDto, RoleModule> fromRoleModuleDto = roleModuleDto -> mapper.map(roleModuleDto, RoleModule.class);
	
	public final static Function<RoleModuleDict, RoleModuleDictDto> toRoleModuleDictDto = roleModuleDict -> mapper.map(roleModuleDict, RoleModuleDictDto.class);
	public final static Function<RoleModuleDictDto, RoleModuleDict> fromRoleModuleDictDto = roleModuleDictDto -> mapper.map(roleModuleDictDto, RoleModuleDict.class);

    public final static Function<BudgetVer, BudgetVerDto> toBudgetVerDto = budgetVer -> mapper.map(budgetVer, BudgetVerDto.class);
    public final static Function<BudgetVerDto, BudgetVer> fromBudgetVerDto = budgetVerDto -> mapper.map(budgetVerDto, BudgetVer.class);
}
