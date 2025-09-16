import pygame
import sys
import random

# Initialize pygame
pygame.init()

# Screen dimensions
WIDTH = 400
HEIGHT = 600
screen = pygame.display.set_mode((WIDTH, HEIGHT))
pygame.display.set_caption("🐤 Flappy Bird Clone")

# Colors
WHITE = (255, 255, 255)
BLUE = (135, 206, 250)
GREEN = (0, 200, 0)

# Game variables
gravity = 0.5
bird_movement = 0
game_active = True
score = 0
high_score = 0

# Fonts
font = pygame.font.SysFont("Arial", 32)

# Bird
bird = pygame.Rect(WIDTH // 4, HEIGHT // 2, 30, 30)

# Pipes
pipe_width = 70
pipe_gap = 150
pipe_list = []
SPAWNPIPE = pygame.USEREVENT
pygame.time.set_timer(SPAWNPIPE, 1200)

def draw_bird():
    pygame.draw.ellipse(screen, (255, 255, 0), bird)  # Yellow bird

def create_pipe():
    height = random.randint(100, 400)
    top_pipe = pygame.Rect(WIDTH, height - pipe_gap - 400, pipe_width, 400)
    bottom_pipe = pygame.Rect(WIDTH, height, pipe_width, 400)
    return top_pipe, bottom_pipe

def move_pipes(pipes):
    for pipe in pipes:
        pipe.centerx -= 5
    return [pipe for pipe in pipes if pipe.right > 0]

def draw_pipes(pipes):
    for pipe in pipes:
        pygame.draw.rect(screen, GREEN, pipe)

def check_collision(pipes):
    for pipe in pipes:
        if bird.colliderect(pipe):
            return False
    if bird.top <= 0 or bird.bottom >= HEIGHT:
        return False
    return True

def display_score():
    score_surface = font.render(f"Score: {score}", True, WHITE)
    screen.blit(score_surface, (10, 10))

    high_surface = font.render(f"High Score: {high_score}", True, WHITE)
    screen.blit(high_surface, (10, 50))

# Game loop
clock = pygame.time.Clock()
while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            pygame.quit()
            sys.exit()

        if event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE and game_active:
                bird_movement = 0
                bird_movement = -8
            if event.key == pygame.K_SPACE and not game_active:
                game_active = True
                pipe_list.clear()
                bird.center = (WIDTH // 4, HEIGHT // 2)
                bird_movement = 0
                score = 0

        if event.type == SPAWNPIPE:
            pipe_list.extend(create_pipe())

    screen.fill(BLUE)

    if game_active:
        # Bird
        bird_movement += gravity
        bird.centery += int(bird_movement)
        draw_bird()

        # Pipes
        pipe_list = move_pipes(pipe_list)
        draw_pipes(pipe_list)

        # Collision
        game_active = check_collision(pipe_list)

        # Score
        score += 0.01
        display_score()

    else:
        game_over_surface = font.render("Game Over! Press SPACE", True, WHITE)
        screen.blit(game_over_surface, (40, HEIGHT // 2))
        if score > high_score:
            high_score = int(score)

    pygame.display.update()
    clock.tick(60)
