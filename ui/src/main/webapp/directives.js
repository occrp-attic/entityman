String.prototype.format = function()
{
   var content = this;
   for (var i=0; i < arguments.length; i++)
   {
        var replacement = '{' + i + '}';
        content = content.replace(replacement, arguments[i]);  
   }
   return content;
};

entityman.directive('fileListing', function($http, entitymanWs, $window, growl) {
  return {
    restrict : 'E',
//    scope : {
//      files : '=',
//      facts : '='
//    },
    templateUrl : 'partials/list_files.html',
    link : function(scope, element, attrs, model) {
      
      scope.getDownloadUrl = function(fileId) {
        return baseUrl + '/entities/getFile/' + fileId;
      };
      
      scope.getFacts = function(fileId) {
        var facts = [];
        scope.facts.forEach(function(fact) {
          if (fact.fileIds.indexOf(fileId) != -1) {
            facts.push(fact);
          }
        });
        return facts;
      };

      scope.statusMapping = {
          '-1' : "Unknown",
          '0' : "New", 
          '1' : "In progress", 
          '2' : "Completed", 
          '3' : "Error"};
      
      scope.getStatus = function(file) {
        return scope.statusMapping[file.status];
      };
      
      scope.deleteFile = function(file) {
        console.log("confirm delete file %o", file);
        setTimeout(function() {
          doDelete = $window.confirm('Are you sure you want to delete file {0} ?'
              .format(file.originalFilename));
          if (doDelete) {
            //          console.log("File List delete file %o", file);
            entitymanWs.removeFile(file.id).success(function(data) {
              growl.info("File {0} deleted".format(file.originalFilename));
              scope.loadWorkspaces(true);
            }).error(function(data) {
              growl.error("Failed to delete file " + file.originalFilename);
            });
          }
        }, 100);
      };
      
    }
  };
});

entityman.directive('workspaceListing', function($http, entitymanWs) {
  return {
    restrict : 'E',
    scope : {
      workspaces : '='
    },
    templateUrl : 'partials/list_workspaces.html',
    link : function(scope, element, attrs, model, $http) {
      
      scope.$watch('workspaces', function(workspaces) {
        console.log("Workspaces in scope %o", workspaces);
        if (!workspaces) return;
        
        var wksps = [];
        workspaces.forEach(function(workspace) {
          var w = {
            name : workspace.name,
            count : workspace.ingestCount
          };
          console.log("Adding workspace %o", w);
          wksps.push(w);
        });
        
        scope.shownWorkspaces = wksps;
        
        // scope.workspaces = wksps.sort(function(a, b) {
        // return a<b;
        // });
        
        scope.activeWorkspace = workspaces[0];
      });
      
      scope.$watch('activeWorkspace', function(activeWorkspace) {
        console.log("trigger entity list update");
      });
      
      scope.makeWorkspace = function() {
        console.log("creating workspace : %s", scope.workspaceNewName);
        
        var res = entitymanWs.makeWorkspace(scope.workspaceNewName);
        console.log("result from mkworkspace : %o", res);
        
        // TODO check for code
        // refresh workspace list
        // activate the new workspace ?
        
      };
      
      scope.selectWorkspace = function(wk, $http) {
        console.log("Selecting active workspace : %s", wk.name);
        scope.activeWorkspace = wk;
        
        // TODO reload entities for active workspace
        
        // alert("Selected workspace : "+wk.name);
        // $modalInstance.close('Opening workspace : '+name);
      };
      
      scope.confirmDeleteWorkspace = function(wk, $http) {
        console.log("Confirm Delete workspace : %s", wk.name);
        alert("Confirm Workspace delete : " + wk.name);
        // $modalInstance.close('ok');
      };
      
      // scope.getWorkspaces = function() {
      // var workspaces = [];
      // console.log("Workspaces in scope %o",scope.workspaces);
      // scope.workspaces.forEach(function(workspace) {
      // if (workspace) {
      // var w = {
      // name: workspace,
      // count: 0
      // };
      // console.log("Adding workspace %o",w);
      // workspaces.push(w);
      // }
      // }
      // );
      // return workspaces;
      // };
    }
  };
});

entityman.directive('entityListing', function() {
  return {
    restrict : 'E',
    scope : {
      factstats : '=',
      entities : '=',
      files : '=',
    },
    templateUrl : 'partials/list_entities.html',
    link : function(scope, element, attrs, model) {
//      console.log("entities ax : %o", scope.entities);
//      console.log("scope.factstats ax : %o", scope.factstats);
      scope.shownEntities = [];
      
      scope.$watch('entities', function(entities) {
//        console.log("LE entities : %o", entities);
        if (!entities) return;
        
        var matching = [];
        entities.forEach(function(e) {
//          console.log("LE entity : %o", e);
          if ([ 'Fact', 'IngestedFile' ].indexOf(e.type) == -1) {
//            console.log("LE entity adding entity: %o", e);
            matching.push(e);
          }
        });
        
//        console.log("LE entities processed : %o", matching);
        scope.shownEntities = matching.sort(function(a, b) {
          var dist = b.fileIds.length - a.fileIds.length;
          if (dist == 0) { return a.label.localeCompare(b.label); }
          return dist;
        });
        
      });
      
      console.log("factstats scope x : %o", scope);
      console.log("factstats xx : %o", scope.factstats);
      
      // scope.factstats = {};
      // scope.$watch('factstats', function(factstats) {
      // console.log("factstats xx : %o", factstats);
      // if (!factstats) return;
      // scope.factstats = factstats;
      // });
      
      console.log("factcount xxx : %o", scope.factcount);
    }
  };
});

entityman.directive('navbar', function($http, entitymanWs, $modal, growl, $location) {
  return {
    restrict : 'E',
    templateUrl : 'partials/navbar.html',
    link : function(scope, element, attrs, model) {
      
      // scope.$watch('workspaces', function(workspaces) {
      // console.log("Navbar Workspaces in scope %o",workspaces);
      // if (!workspaces) return;
      //
      // var wksps = [];
      // workspaces.forEach(function(workspace) {
      // var w = {
      // name: workspace.name,
      // count: workspace.ingestCount
      // };
      // console.log("Navbar Adding workspace %o",w);
      // wksps.push(w);
      // });
      //
      // scope.shownWorkspaces = wksps;
      // });
      
      // scope.$watch('activeWorkspace', function(activeWorkspace) {
      // console.log("trigger entity list update");
      // //TODO
      // });
      
      scope.makeWorkspace = function() {
        console.log("creating workspace : %s", scope.workspaceNewName);
        
        entitymanWs.makeWorkspace(scope.workspaceNewName).success(
            function(data, status, headers, config) {
              console.log("Successfully created workspace %s : %o", scope.workspaceNewName, data);
              growl.info("Workspace created");
              scope.activeWorkspace = data;
              scope.loadWorkspaces(false);
              scope.workspaceNewName = "";
            }).error(function(data, status, headers, config) {
          console.log("Error creating workspace %s : %o", scope.workspaceNewName, data);
          growl.info("Failed to create workspace");
        });
      };
      
      scope.selectWorkspace = function(wk) {
        console.log("Selecting active workspace : %o", wk);
        scope.activeWorkspace = wk;
        $location.url("/");
      };
      
      scope.uploadFile = function() {
        console.log("Opening Upload dialog for %o", scope.activeWorkspace);
        var modalInstance = $modal.open({
//          scope : {
//            activeWorkspace : scope.activeWorkspace
//          },
          templateUrl : 'upload.html',
          controller : 'UploadController',
          resolve: {
            activeWorkspace: function () {
              return scope.activeWorkspace;
            },
            baseScope: function () {
              return scope;
            }
          }
        });
        
        modalInstance.result.then(function () {
          console.log("modal Upload reloading workspace");
          //scope.loadWorkspace(scope.activeWorkspace);
          scope.loadWorkspaces(true);
        }, function () {
          console.log("modal Upload closed");
        });
      };
      
    }
  };
});

entityman.directive('infoWorkspace', function($http, entitymanWs, $window, growl) {
  return {
    restrict : 'E',
    templateUrl : 'partials/info_workspace.html',
    link : function(scope, element, attrs, model) {
      
//      scope.$watch('activeWorkspace', function(aw) {
//        console.log("infoWorkspace active workspace changed %o", aw);
//        if (!aw) return;
//        
//        scope.workspace = scope.activeWorkspace;
//      });
      
      scope.refreshWorkspace = function() {
        console.log("infoWorkspace refreshing workspace %o", scope.activeWorkspace);
        scope.loadWorkspace(scope.activeWorkspace);
      };
      
      scope.updateWorkspace = function() {
        doUpdate = $window.confirm('Are you sure you want to update ?');
        if (doUpdate) {
          console.log("infoWorkspace updating workspace %o", scope.activeWorkspace);
          growl.warn("Not yet implemented");
          // TODO call update with wait, then reload
          // workspace
        }
      };
      
      scope.deleteWorkspace = function() {
        doDelete = $window.confirm('Are you sure you want to delete workspace ' + scope.activeWorkspace.name + ' ?');
        if (doDelete) {
          console.log("infoWorkspace delete workspace %o", scope.activeWorkspace);
          entitymanWs.removeWorkspace(scope.activeWorkspace.name).success(function(data){
            growl.info("Workspace '{0}' removed ({1} files, {2} entities)"
                .format(scope.activeWorkspace.name,data.ingestCount, data.entitiesCount));
            scope.loadWorkspaces(true);
          }).error(function(data){
            growl.error("Failed to delete workspace "+scope.activeWorkspace.name);
          });
          
          
        }
      };
    }
  };
});

entityman.directive('someDirective', function() {
  return {
    scope : {},
    bindToController : true,
    controller : function() {
      this.name = 'Pascal';
    },
    controllerAs : 'ctrl',
    template : '<div>{{ctrl.name}}</div>'
  };
});


entityman.directive('tooltip', function(){
  return {
      restrict: 'A',
      link: function(scope, element, attrs){
          $(element).hover(function(){
              // on mouseenter
              $(element).tooltip('show');
          }, function(){
              // on mouseleave
              $(element).tooltip('hide');
          });
      }
  };
});