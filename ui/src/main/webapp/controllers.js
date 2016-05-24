entityman.controller('BaseController', function ($scope, $rootScope, $modal,entitymanWs,growl) {
  $rootScope.routeLoading = true;

  $rootScope.$on("$routeChangeStart", function (event, next, current) {
    $rootScope.routeLoading = true;
  });

  $rootScope.$on("$routeChangeSuccess", function (event, next, current) {
    $rootScope.routeLoading = false;
  });

//  $scope.uploadFile = function() {
//    var d = $modal.open({
//      templateUrl: 'upload.html',
//      controller: 'UploadController'
//    });
//  };

  $scope.loadWorkspaces = function(doInit) {
//    $scope.routeLoading = true;
    console.log("BC load workspaces info");
    entitymanWs.loadWorkspaces()
    .success(function(data, status, headers, config) {
      console.log("BC Loaded workspaces %o",data);
      if (doInit) $scope.activeWorkspace = null;
      $scope.workspaces = data;
    }).error(function(data, status, headers, config) {
      console.error("BC Failed to load workspaces");
      growl.error('Error loading workspaces.',{title: 'Error!'});
    });
  };
  
  $scope.$watch('workspaces', function(workspaces) {
    console.log("BC workspaces changed %o", workspaces);
    if (!workspaces) return;

    if (!$scope.activeWorkspace || $scope.activeWorkspace===null) {
      console.log("BC init active workspace to %o",$scope.workspaces[0]);
      $scope.activeWorkspace = $scope.workspaces[0];
    }
  });

  
  $scope.loadWorkspace = function(w) {
    if (!w) return;
    console.log("BC load workspace %o",w);
    
    var results = [];
    entitymanWs.loadEntities(w).success(function(data){
//      console.log("Data.o : %o", data.o);
      angular.forEach(data.o, function(entities, type) {
//        console.log("Entity type %s : %o", type, entities);
        if (angular.isArray(entities[1])) {
          angular.forEach(entities[1], function(entity) {
            if (entity.fileIds) {
              entity.type = type;
              results.push(entity);
            }
          });
        }
      });
//      console.log("Entities : %o",results);
      $scope.entities = results;
      
//      console.log("Files : %o",data.o.IngestedFile);
      $scope.files = data.o.IngestedFile[1];
      $scope.activeWorkspaceInfo = data.o.workspace;
//      $scope.activeWorkspace = data.o.workspace; this cycles
      
    }).error(function(){
      console.error("BC Error loading workspace %o",w);
      growl.error('Error loading workspace.',{title: 'Error!'});
      
    });

    entitymanWs.loadFactStats(w).success(function(data){
      $scope.factstats = data.o;
    }) ;
  };
  
  $scope.loadWorkspaces(false);
//  console.log("Activating workspace %o",$scope.activeWorkspace);

  $scope.$watch('activeWorkspace',function(aw) {
    console.log("BC active workspace changed to %o",aw);
    $scope.loadWorkspace(aw);
  });
  
  
//  console.log("factstats z : %o", $scope.factstats);
  $scope.facts = [];
  
});


entityman.controller('UploadController', function($scope, $location, $modalInstance, 
    Upload, growl, activeWorkspace, baseScope) {
  $scope.files = [];
  $scope.filenames = '';
  $scope.uploadMessage = null;

  $scope.close = function() {
    $modalInstance.dismiss('cancel');
  };

  $scope.setFiles = function(files) {
    $scope.files = files;
    $scope.uploadMessage = null;
    var names = $scope.files.map(function(f) {
      return f.name;
    });
    $scope.filenames = names.join(', ');
  };

  $scope.upload = function() {
    console.log("Adding files to workspace : %o", activeWorkspace);
    Upload.upload({
      url: baseUrl + '/entities/ingestSync/'+activeWorkspace.name,
      file: $scope.files
    }).progress(function (evt) {
      var pct = parseInt((evt.loaded / evt.total) * 100);
      if (pct >= 99) {
        $scope.uploadMessage = 'Processing the document...';
      } else {
        $scope.uploadMessage = pct + '% uploaded';
      }
    }).success(function (data, status, headers, config) {
      console.log("UC files uploaded %o",data);
//      for (var i in data.o) {
//        var entity = data.o[i];
//        if (entity['class'].indexOf('IngestedFile') != -1) {
//          $location.path('/file/' + entity.id);
//        }
//      }
//      console.log("UC reloading workspace %o",activeWorkspace);
//      baseScope.loadWorkspace();
      growl.info("Files ingested {0}".format($scope.files.length));
      $modalInstance.close('ok');
    }).error(function (data, status, headers, conf) {
      console.error("UC FAILED files uploaded %o",data);
      $scope.setFiles([]);
      growl.error("Failed to upload files");
    });
  };
});

entityman.controller('IndexController', function($scope, $location, $q,
//		entities,
//		factstats, 
//		workspaces,
		$modal,entitymanWs,growl) {
//	$scope.routeLoading = true;

//	$scope.loadWorkspaces = function() {
//		console.log("IC load workspaces info");
//		entitymanWs.loadWorkspaces()
//		.success(function(data, status, headers, config) {
//			console.log("Loaded workspaces %o",data);
//			$scope.workspaces = data;
//		}).error(function(data, status, headers, config) {
//			console.error("Failed to load workspaces");
//			growl.error('Error loading workspaces.',{title: 'Error!'});
//		});
//	};
//	
//    $scope.$watch('workspaces', function(workspaces) {
//		console.log("IC workspaces changed %o", workspaces);
//		if (!workspaces) return;
//
//		if (!$scope.activeWorkspace) {
//			console.log("IC init active workspace to %o",$scope.workspaces[0]);
//			$scope.activeWorkspace = $scope.workspaces[0];
//		}
//	});
//
//	
//	$scope.loadWorkspace = function(w) {
//		if (!w) return;
//		console.log("IC load workspace %o",w);
//		
//		var results = [];
//		entitymanWs.loadEntities(w).success(function(data){
//      console.log("Data.o : %o", data.o);
//			angular.forEach(data.o, function(entities, type) {
//			  console.log("Entity type %s : %o", type, entities);
//				if (angular.isArray(entities[1])) {
//					angular.forEach(entities[1], function(entity) {
//						if (entity.fileIds) {
//							entity.type = type;
//							results.push(entity);
//						}
//					});
//				}
//			});
//			console.log("Entities : %o",results);
//			$scope.entities = results;
//			
//      console.log("Files : %o",data.o.IngestedFile);
//			$scope.files = data.o.IngestedFile[1];
//			
//		}).error(function(){
//			console.error("Error loading workspace %o",w);
//			growl.error('Error loading workspace.',{title: 'Error!'});
//			
//		});
//
//		entitymanWs.loadFactStats(w).success(function(data){
//			$scope.factstats = data.o;
//		}) ;
//	};
//	
//	$scope.loadWorkspaces();
////	console.log("Activating workspace %o",$scope.activeWorkspace);
//
//	$scope.$watch('activeWorkspace',function(aw) {
//		console.log("IC active workspace changed to %o",aw);
//		$scope.loadWorkspace(aw);
//	});
//	
//	
//	console.log("factstats z : %o", $scope.factstats);
//	$scope.facts = [];
	
//    $scope.routeLoading = false;
    
    
//	$scope.makeWorkspace = function() {
//		console.log("creating workspace : %s",scope.workspaceNewName);
//		
//		var res = entitymanWs.makeWorkspace($scope.workspaceNewName);
//		console.log("result from mkworkspace : %o",res);
//		
//		// TODO check for code
//		// refresh workspace list
//		// activate the new workspace ?
//		
//	};
//
//	$scope.selectWorkspace = function(wk) {
//		console.log("Selecting active workspace : %o",wk);
//		$scope.activeWorkspace = wk;
//		// TODO reload entities for active workspace
//	};
	
//    $scope.uploadFile = function() {
//    	console.log("Opening Upload dialog for %o", $scope.activeWorkspace);
//        var d = $modal.open({
//          templateUrl: 'upload.html',
//          controller: 'UploadController',
//          resolve: {
//            activeWorkspace: function () {
//              return $scope.activeWorkspace;
//            }
//          }
//        });
//      };
    
});


entityman.controller('FileController', function ($scope, $location, $routeParams, 
    entitymanWs, $window, growl
//    , file, entities
    ) {
  var fileId = $routeParams.id;
  $scope.fileId = fileId;
  
  $scope.loadFile = function(fileId) {
    if (!fileId) return;
    console.log("FC load file %o",fileId);
    
    var results = [];
    entitymanWs.loadFileEntities(fileId).success(function(data){
//      console.log("Data.o : %o", data.o);
      angular.forEach(data.o, function(entities, type) {
//        console.log("Entity type %s : %o", type, entities);
        if (angular.isArray(entities[1])) {
          angular.forEach(entities[1], function(entity) {
            if (entity.fileIds) {
              entity.type = type;
              results.push(entity);
            }
          });
        }
      });
//      console.log("Entities : %o",results);
      $scope.entities = results;
      
//      console.log("Files : %o",data.o.IngestedFile);
      entitymanWs.loadFile(fileId).success(function(data){
        $scope.file = data.o;
      });
    }).error(function(){
      console.error("FC Error loading file %o",fileId);
      growl.error('Error loading file details.',{title: 'Error!'});
    });

    entitymanWs.loadFactStats($scope.activeWorkspace).success(function(data){
      $scope.factstats = data.o;
    }) ;
  }
  
  $scope.loadFile(fileId);
  
  $scope.deleteFile = function() {
    console.log("confirm delete file %o", $scope.fileId);
    setTimeout(function() {
      doDelete = $window.confirm('Are you sure you want to delete file {0} ?'
          .format($scope.file.originalFilename));
      if (doDelete) {
        entitymanWs.removeFile($scope.fileId).success(function(data) {
          growl.info("File {0} deleted".format($scope.file.originalFilename));
          $scope.loadWorkspaces(true);
          $location.url("/");
        }).error(function(data) {
          growl.error("Failed to delete file {0}".format($scope.file.originalFilename));
        });
      }
    }, 100);
  };

  $scope.getDownloadUrl = function(fileId) {
    return baseUrl + '/entities/getFile/' + fileId;
  };

});


entityman.controller('EntityController', function ($scope, $location, $routeParams, data) {
  var entityId = $routeParams.id;
  $scope.entityType = $routeParams.type;
  $scope.entity = data.entity;
  $scope.facts = data.facts;
  $scope.files = data.files;
});

entityman.controller('LoginController', function ($scope, $location, $routeParams, data) {
});
