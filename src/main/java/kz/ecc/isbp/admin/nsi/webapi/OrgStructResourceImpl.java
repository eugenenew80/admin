package kz.ecc.isbp.admin.nsi.webapi;

import static javax.servlet.http.HttpServletResponse.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.nsi.entity.OrgStruct;
import kz.ecc.isbp.admin.nsi.dto.OrgStructDto;
import kz.ecc.isbp.admin.nsi.service.OrgStructService;
import static kz.ecc.isbp.admin.common.dto.mapper.Mapper.*;


@RequestScoped
@Path("/orgStructs")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class OrgStructResourceImpl {
	
	@GET
	public Response getAll() {
		List<OrgStructDto> orgStructListDto = orgStructService.findAll().stream()
            .map( toOrgStructDto )
            .collect(Collectors.toList());
		
		return Response.ok()
            .entity(new GenericEntity<Collection<OrgStructDto>>(orgStructListDto){})
            .build();
	}
	
	
	@GET
	@Path("{orgStructId : \\d+}")
	public Response get(@PathParam("orgStructId") Long orgStructId) {
        Optional<OrgStruct> orgStruct = Optional.ofNullable(orgStructService.findById(orgStructId));
        return Response.ok()
            .entity( orgStruct.map(toOrgStructDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
            .build();
	}
	
	
	@GET
	@Path("{orgStructName}")
	public Response getByName(@PathParam("orgStructName") String orgStructName) {
        Optional<OrgStruct> orgStruct = Optional.ofNullable(orgStructService.findByName(orgStructName));
        return Response.ok()
                .entity( orgStruct.map(toOrgStructDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
                .build();
	}
	
	
	@Inject private OrgStructService orgStructService;
}
