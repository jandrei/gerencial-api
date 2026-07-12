package br.com.gerencial.seguranca.filtes;

import org.eclipse.microprofile.openapi.OASFactory;
import org.eclipse.microprofile.openapi.OASFilter;
import org.eclipse.microprofile.openapi.models.Operation;
import org.eclipse.microprofile.openapi.models.media.Schema.SchemaType;
import org.eclipse.microprofile.openapi.models.parameters.Parameter;
import java.util.ArrayList;
import java.util.List;

public class SwaggerHeaderFilter implements OASFilter {

    @Override
    public Operation filterOperation(Operation operation) {
        // Inicializa a lista de parâmetros caso esteja nula
        if (operation.getParameters() == null) {
            operation.setParameters(new ArrayList<>());
        }

        // Instancia o parâmetro do header
        Parameter userEmailHeader = OASFactory.createParameter()
                .name("X-Dev-User-Email")
                .in(Parameter.In.HEADER)
                .required(true)
                .description("Identificador do usuario para a requisição, funciona apenas em ambientes nao produtivos")
                .schema(OASFactory.createSchema().type(List.of(SchemaType.STRING)));

        Parameter tenantHeader = OASFactory.createParameter()
                .name("X-Organization-Id")
                .in(Parameter.In.HEADER)
                .required(true)
                .description("Identificador do Tenant para a requisição")
                .schema(OASFactory.createSchema().type(List.of(SchemaType.STRING)));

        operation.addParameter(userEmailHeader);
        operation.addParameter(tenantHeader);

        // OBRIGATÓRIO EM MP OPENAPI 4.1: Retornar o objeto modificado
        return operation;
    }

}