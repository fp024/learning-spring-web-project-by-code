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

<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
        crossorigin="anonymous"></script>

<script>
  $(document).ready(function () {
    $("#uploadBtn").on("click", function (e) {
      let formData = new FormData();

      let inputFile = $("input[name='uploadFile']");

      let files = inputFile[0].files;

      console.log(files);

      // 파일 데이터를 폼 데이터에 추가
      for (let i = 0; i < files.length; i++) {
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
