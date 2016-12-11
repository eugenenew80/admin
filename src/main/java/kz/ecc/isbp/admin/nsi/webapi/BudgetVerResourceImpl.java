package kz.ecc.isbp.admin.nsi.webapi;

import static javax.servlet.http.HttpServletResponse.*;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import kz.ecc.isbp.admin.nsi.dto.BudgetVerDto;
import kz.ecc.isbp.admin.nsi.entity.BudgetVer;
import kz.ecc.isbp.admin.nsi.service.BudgetVerService;
import static kz.ecc.isbp.admin.common.dto.mapper.Mapper.*;


@RequestScoped
@Path("/budgetVers")
@Produces({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
@Consumes({"application/json;charset=utf-8", "application/xml;charset=utf-8"})
public class BudgetVerResourceImpl {

	@GET
	public Response getAll() {
		List<BudgetVerDto> budgetVerListDto = budgetVerService.findAll().stream()
			.map(toBudgetVerDto)
			.collect(Collectors.toList());
		
		return Response.ok()
				.entity(new GenericEntity<Collection<BudgetVerDto>>(budgetVerListDto){})
				.build();
	}
	
	
	@GET
	@Path("{budgetVerId : \\d+}")
	public Response get(@PathParam("budgetVerId") Long budgetVerId) {
		Optional<BudgetVer> budgetVer = Optional.ofNullable(budgetVerService.findById(budgetVerId));
		return Response.ok()
			.entity( budgetVer.map(toBudgetVerDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
			.build();
	}
	
	
	@GET
	@Path("{budgetVerName}")
	public Response getByName(@PathParam("budgetVerName") String budgetVerName) {
		Optional<BudgetVer> budgetVer = Optional.ofNullable(budgetVerService.findByName(budgetVerName));
		return Response.ok()
				.entity( budgetVer.map(toBudgetVerDto).orElseThrow( () -> new WebApplicationException(SC_NOT_FOUND)) )
				.build();
	}
	
		
	@Inject private BudgetVerService budgetVerService; 
}
