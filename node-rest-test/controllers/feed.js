exports.getPosts = (req, res, next) => {
    res.status(200).json({
        posts: [{title: 'First Post', content: 'This is the first post!'}]
    });
};

exports.createPost = (req, res, next) => {
    const md5 = req.body.MD5Password;
    const workerNum = req.body.workerNum;

    // Create post in db
    res.status(200).json({
      message: "Post created successfully!",
      post: {
        id: new Date().toISOString(),
        md5: md5,
        workerNum: workerNum,
      },
    });
}

