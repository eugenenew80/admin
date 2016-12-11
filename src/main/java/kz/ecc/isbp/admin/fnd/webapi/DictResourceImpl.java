package kz.ecc.isbp.admin.fnd.webapi;

import static javax.servlet.http.HttpServletResponse.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.fnd.dto.DictDto;
import kz.ecc.isbp.admin.fnd.entity.Dict;
import kz.ecc.isbp.admin.fnd.service.DictService;
import static kz.ecc.isbp.admin.common.dto.mapper.Mapper.*;

@RequestScoped
@Path("/dicts")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class DictResourceImpl {
	
	@GET
	public Response getAll() {		
		List<DictDto> dictListDto = dictService.findAll().stream()
			.map(toDictDto)
			.collect(Collectors.toList());
		
		return Response.ok()
				.entity(new GenericEntity<Collection<DictDto>>(dictListDto){})
				.build();
	}
	
	
	@GET
	@Path("{dictId : \\d+}")
	public Response get(@PathParam("dictId") Long dictId) {
		Optional<Dict> dict = Optional.ofNullable(dictService.findById(dictId));
		return Response.ok()
			.entity( dict.map(toDictDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@GET
	@Path("{dictName}")
	public Response getByName(@PathParam("dictName") String dictName) {
		Optional<Dict> dict = Optional.ofNullable(dictService.findByName(dictName));
		return Response.ok()
			.entity( dict.map(toDictDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}

	@Inject private DictService dictService; 
}
