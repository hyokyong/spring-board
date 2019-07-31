package pj1;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pj1.File;

public interface FileMapper {
    File selectByAid(int a_id);
    File selectByFid(int f_id);
    void insert(File file);
    void delete(int f_id);
    void deleteByAid(int a_id);
    void update(File file);
}
