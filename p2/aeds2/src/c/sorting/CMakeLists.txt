cmake_minimum_required(VERSION 3.15...3.30)

if(NOT CMAKE_C_COMPILER)
    set(CMAKE_C_COMPILER "c:/msys64/mingw64/bin/gcc.exe")
endif()

project(Sorting 
        VERSION 0.1
        LANGUAGES C
        DESCRIPTION "Sorting algorithms")

include_directories(include)

file(GLOB LIB_SOURCES "lib/*.c")

add_executable(main app/main.c ${LIB_SOURCES})