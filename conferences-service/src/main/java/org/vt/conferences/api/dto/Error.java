package org.vt.conferences.api.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Vlad Trofymets on 07.05.2021
 */
@Getter
@RequiredArgsConstructor
public class Error {

    @NonNull
    private final String message;

}
