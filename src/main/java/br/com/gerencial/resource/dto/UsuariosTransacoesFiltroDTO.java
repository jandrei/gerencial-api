package br.com.gerencial.resource.dto;

import io.quarkus.panache.common.Page;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;

import java.time.LocalDateTime;

public record UsuariosTransacoesFiltroDTO(@QueryParam("page") @DefaultValue("0") int page,
                                          @QueryParam("size") @DefaultValue("10") int size,
                                          @QueryParam("userId") Long userId,
                                          @QueryParam("from") String from,
                                          @QueryParam("to") String to,
                                          @QueryParam("tagId") java.util.List<Long> tagIds,
                                          @QueryParam("requireAllTags") @DefaultValue("false") boolean requireAllTags) {
    public LocalDateTime fromDateTime() {
        return (from != null && !from.isBlank()) ? LocalDateTime.parse(from) : null;
    }

    public LocalDateTime toDateTime() {
        return (to != null && !to.isBlank()) ? LocalDateTime.parse(to) : null;
    }

    public Page getPage() {
        return Page.of(page, size);
    }
}
