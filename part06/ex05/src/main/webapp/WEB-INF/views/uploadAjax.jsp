<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8"/>
  <title>Ajax 파일 업로드 테스트 페이지</title>
</head>

<body>
<h1>Upload with Ajax</h1>

<div class="uploadDiv">
  <input type="file" name="uploadFile" multiple>
</div>

<button id="uploadBtn">Upload</button>

<script src="https://code.jquery.com/jquery-3.6.0.js"
        integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
        crossorigin="anonymous"></script>

<script>
  $(document).ready(function () {
    var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
    var maxSize = 1024 * 1024 * 5; // web.xml에 단일 파일 최대 크기를 20MB로 정의했었는데, 5MB로 해보자.

    function checkExtension(fileName, fileSize) {
      if (fileSize > maxSize) {
        alert("파일 사이즈 초과");
        return false;
      }

      if (regex.test(fileName)) {
        alert("해당 종류의 파일은 업로드할 수 없습니다.");
        return false;
      }
      return true;
    }

    $("#uploadBtn").on("click", function (e) {
      var formData = new FormData();
      var inputFile = $("input[name='uploadFile']");
      var files = inputFile[0].files;

      console.log(files);

      // 파일 데이터를 폼 데이터에 추가

      for (var i = 0; i < files.length; i++) {
        if (!checkExtension(files[i].name, files[i].size)) {
          return false;
        }
        formData.append("uploadFile", files[i]);
      }

      $.ajax({
        url: "/uploadAjaxAction",
        processData: false,
        contentType: false,
        data: formData,
        type: "POST",
        success: function (result) {
          alert("Uploaded: " + result);
        }
      });
    });
  });
</script>

</body>
</html>
