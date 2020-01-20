function dropList() {
    var jsondata = JSON.parse("C:\\Users\\Abyeti\\eclipse-workspace\\Library Management System\\out\\artifacts\\LibraryManagementSystem\\WEB-INF\\vendorData.json");

    for(var i=0;i<jsondata.length;i++){
        var data = jsondata[i];
        for(var j=0;j<data.length;j++){
            var bookData = data[i];
            console.log(bookData.bookName);
        }
    }

    console.log("Hello");
}

