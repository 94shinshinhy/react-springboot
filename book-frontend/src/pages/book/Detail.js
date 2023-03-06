import React, { useEffect, useState } from 'react';
import { Button } from 'react-bootstrap';
import { useNavigate, useParams } from 'react-router-dom';

const Detail = (props) => {
  const navigate = useNavigate();

  console.log(1, props);
  //const id = props.match.params.id;
  const { id } = useParams();

  const [book, setBook] = useState({
    id: '',
    title: '',
    author: '',
  });

  useEffect(() => {
    const findByIdUrl = 'http://localhost:8080/book/' + id;

    fetch(findByIdUrl)
      .then((res) => res.json())
      .then((res) => {
        setBook(res);
      });
  }, []);

  // 이 페이지에서는 굳이 id를 받을 필요가 없음
  const deleteBook = (id) => {
    const DeleteUrl = 'http://localhost:8080/book/' + id;

    fetch(DeleteUrl, {
      method: 'DELETE',
    })
      .then((res) => res.text())
      .then((res) => {
        if (res === 'ok') {
          navigate('/');
        } else {
          alert('삭제 실패');
        }
      });
  };

  const updateBook = () => {
    navigate('/updateForm/' + id);
  };

  return (
    <div>
      <h1>상세보기</h1>
      <Button onClick={updateBook}>수정</Button>
      <Button variant="danger" onClick={() => deleteBook(book.id)}>
        삭제
      </Button>
      <hr />
      <h3>{book.author}</h3>
      <h1>{book.title}</h1>
    </div>
  );
};

export default Detail;
