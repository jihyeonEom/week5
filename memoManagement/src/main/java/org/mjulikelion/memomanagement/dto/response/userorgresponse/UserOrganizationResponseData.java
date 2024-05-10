package org.mjulikelion.memomanagement.dto.response.userorgresponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.memomanagement.model.UserOrganization;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserOrganizationResponseData {
    private List<UserOrganization> userOrganizations;
}
