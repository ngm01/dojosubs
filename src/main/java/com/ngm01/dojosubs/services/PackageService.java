package com.ngm01.dojosubs.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ngm01.dojosubs.models.DojoPackage;
import com.ngm01.dojosubs.repositories.PackageRepository;

@Service
public class PackageService {
	
	private PackageRepository packageRepo;
	
	public PackageService(PackageRepository packageRepo) {
		this.packageRepo = packageRepo;
	}
	
	public void createPackage(DojoPackage newPackage) {
		packageRepo.save(newPackage);
	}
	
	public DojoPackage readOne(Long id) {
		return packageRepo.findOne(id);
	}
	
	public List<DojoPackage> readAll(){
		return (List<DojoPackage>) packageRepo.findAll();
	}
	
	public void updatePackage(DojoPackage updatedPackage) {
		packageRepo.save(updatedPackage);
	}
	
	public void deletePackage(Long id) {
		packageRepo.delete(id);
	}
	
}
