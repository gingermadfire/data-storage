package com.gingermadfire.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MissingFileExtensionException extends RuntimeException{

    private final String message;
}
