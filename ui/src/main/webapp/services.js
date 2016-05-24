entityman.factory('entitymanWs', function($http, $route) {
  var entitymanWs = {
    activeWorkspace : null,
    makeWorkspace : function(name) {
      console.log("make workspaces : %s", name);
      var user = "";
      return $http.get(baseUrl + '/entities/mkworkspace/' + name);
    },
    removeWorkspace : function(name) {
      console.log("remove workspace : %s", name);
      var user = "";
      return $http.get(baseUrl + '/entities/rmworkspace/' + name);
    },
    removeFile : function(fileId) {
      console.log("remove File : %s", fileId);
      var user = "";
      return $http.get(baseUrl + '/entities/deletefile/' + fileId);
    },
    loadWorkspaces : function() {
      console.log("loading workspaces");
      var user = "";
      return $http.get(baseUrl + '/entities/workspaces/');
    },
    loadEntities : function(w) {
      var activeWorkspaceName = (typeof w === "undefined") ? "default" : w.name;
      console.log("loading entities for workspace : %s", activeWorkspaceName);
      return $http.get(baseUrl + '/entities/workspace/' + activeWorkspaceName);
    },
    loadFactStats : function(w) {
      var activeWorkspaceName = (typeof w === "undefined") ? "default" : w.name;
      console.log("loading factstats for workspace : %s", activeWorkspaceName);
      return $http.get(baseUrl + '/entities/factstats/' + activeWorkspaceName);
    },
    loadFileEntities : function(fileId) {
      return $http.get(baseUrl + '/entities/AllByFileId/' + fileId);
    },
    loadFile : function(fileId) {
      return $http.get(baseUrl + '/entities/byId/IngestedFile/' + fileId);
    }
  };
  
  return entitymanWs;
});
