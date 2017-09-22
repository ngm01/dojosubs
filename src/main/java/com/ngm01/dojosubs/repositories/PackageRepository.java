package com.ngm01.dojosubs.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ngm01.dojosubs.models.DojoPackage;

@Repository
public interface PackageRepository extends CrudRepository<DojoPackage, Long> {

}
