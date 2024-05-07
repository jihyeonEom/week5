package org.mjulikelion.memomanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.mjulikelion.memomanagement.model.User;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class UserListResponseData {
    private List<User> users;
}
