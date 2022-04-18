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

<script>
  document.addEventListener('DOMContentLoaded', () => {
    document.querySelector('button').addEventListener('click', () => {
      const formData = new FormData()
      const inputFile = document.querySelector('input[name="uploadFile"]')
      const files = inputFile.files // input이 1개가 명백하니, querySelector를 사용했고, 이것은 배열을 반환하지 않으므로, 배열참조를 제거하자!

      console.log(files)

      // 파일 데이터를 폼 데이터에 추가
      for (let i = 0; i < files.length; i++) {
        formData.append("uploadFile", files[i])
      }

      fetch("/uploadAjaxAction", {
        method: "POST",
        body: formData
      }).then(response => { // https://developer.mozilla.org/en-US/docs/Web/API/Response/text
        return response.text().then(text => {
          alert("Uploaded: " + text)
        })
      })
    })
  })
</script>

</body>
</html>
